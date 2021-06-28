package it.polimi.ingsw.model;

import it.polimi.ingsw.client.reducedmodel.ReducedMarble;
import it.polimi.ingsw.commons.ColorMarble;
import it.polimi.ingsw.commons.LeaderEffect;
import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.commons.Effect;
import it.polimi.ingsw.model.marketTray.*;
import it.polimi.ingsw.utils.MarbleDestination;
import it.polimi.ingsw.utils.messages.serverMessages.Updates.PositioningUpdate;
import it.polimi.ingsw.utils.messages.serverMessages.Updates.TakeResourcesFromMarketUpdate;
import it.polimi.ingsw.utils.Pair;
import it.polimi.ingsw.utils.messages.clientMessages.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Take Resources From Market action is organized as follows: the current
 * player selects the marbles he wants to take from the market, then he attempts to perform a first positioning
 * phase. If he makes a mistake while positioning the resources the first time, they are placed in the pending
 * resources. Afterwards he has a second opportunity to specify how to place the resources he failed to place.
 * If he fails again, the remaining resources are automatically discarded and the other players move by the
 * correspondent number of positions on their faith track.
 */

public class TakeResourcesFromMarket implements AbstractTurnPhase {

    /**
     * Each resource associated to the marble and taken from the market has a possible destination
     */
    private List<Pair<ResourceType, MarbleDestination>> whereToPutResources = new ArrayList<>();
    /**
     * Resources wrongly positioned while taking resources from market
     */
    private List<ResourceType> pendingResources = new ArrayList<>();
    private int faith = 0;
    private int discardedResources = 0;

    /**
     * This is the main method of the class. It makes consistency check to avoid corruption.
     * @param turn represents the turn in which the action is being performed.
     * @param takeResourcesFromMarketMessage message containing all the instructions coming from the player
     * @throws InvalidActionException if a normal action has been already performed in the turn.
     * @throws WhiteEffectMismatchException if the player has selected a wrong number of effects or selected ones don't match with player's leader effects.
     * @throws NeedPositioningException if a certain resource cannot be placed in warehouse and required further actions from the player.
     */
    @Override
    public void takeResourcesFromMarket(Turn turn, TakeResourcesFromMarketMessage takeResourcesFromMarketMessage) throws InvalidActionException, WhiteEffectMismatchException, NeedPositioningException {
        if(turn.isDoneNormalAction())
            throw new InvalidActionException();
        if(checkValidWhiteEffects(turn,takeResourcesFromMarketMessage.getWhiteEffects(), takeResourcesFromMarketMessage.getRequestedMarbles())) {

            List<Marble> obtainedMarbles = turn.getGame().getMarketTray().
                    takeMarbles(takeResourcesFromMarketMessage.getPlayerChoice(), takeResourcesFromMarketMessage.getIndex()-1);

            //perform convert marble effect
            convertWhiteMarbles(turn, obtainedMarbles, takeResourcesFromMarketMessage.getWhiteEffects());

            //convert a List of Pair<Marble,MarbleDestination> in a List of Pair<ResourceType,MarbleDestination>
            convertMarblesToResources(obtainedMarbles, takeResourcesFromMarketMessage.getWhereToPutMarbles());

            handlePositioning(turn.getPlayer().getPersonalBoard().getWarehouse());
            turn.getGame().getMarketTray().clearWhiteMarbleEffect();
            moveCurrPlayerMarker(turn);
            if(pendingResources.size()>0)
                throw new NeedPositioningException(pendingResources);
            else {
                turn.normalActionDone();
                turn.getGame().notifyUpdate(new TakeResourcesFromMarketUpdate(turn.getPlayer().getUser(),
                        turn.getPlayer().getReducedPersonalBoard(),
                        turn.getGame().getMarketTray().getReducedVersion(),
                        getEarnedResources(),faith));
                concludeTurnPhase(turn);
            }
        } else throw new WhiteEffectMismatchException();
    }


    /**
     * Sets effect for white marbles as indicated
     * @param turn in which the action is performed
     * @param marbles the list of marbles containing some white marbles to convert.
     * @param whiteEffects the list containing, for each white marble, the index of the convert marble leader card to active.
     */
    private void convertWhiteMarbles(Turn turn, List<Marble> marbles, List<Integer> whiteEffects) {
        List<ResourceType> whiteMarbleEffects = turn.getPlayer().getActiveEffects().stream().filter(x -> x.getEffect()==Effect.CMARBLE).
                map(LeaderEffect::getType).collect(Collectors.toList());
        int i = 0;
        for(Marble m : marbles) {
            if(m.getColor()== ColorMarble.WHITE) {
                if(whiteMarbleEffects.size()==1) {
                    WhiteMarble y = (WhiteMarble) m;
                    y.setEffect(convertTypeToMarbleEffect(whiteMarbleEffects.get(0)));
                } else if(whiteMarbleEffects.size() == 2) {
                    WhiteMarble y = (WhiteMarble) m;
                    y.setEffect(convertTypeToMarbleEffect(whiteMarbleEffects.get(whiteEffects.get(i)-1)));
                    i++;
                }
            }
        }
    }

    /**
     * Converts each marble obtained to the corresponding {@link ResourceType}.
     * @param marbles the list of the marbles obtained.
     * @param whereToPutMarbles the list containing the indications on where to put each marble.
     */
    private void convertMarblesToResources(List<Marble> marbles, List<Pair<ReducedMarble,MarbleDestination>> whereToPutMarbles) {
        for(Marble p : marbles) {
            if (p.getColor() == ColorMarble.RED)
                faith++;
        }
        ResourceType resourceType;
        for(int i = 0; i < marbles.size(); i++) {
            try {
                resourceType = marbles.get(i).addResources();
                whereToPutResources.add(new Pair<>(resourceType, whereToPutMarbles.get(i).getValue()));
            } catch (NoSuchResourceTypeException ignored) {}
        }
    }

    /**
     * Method used to convert the Resource Type associated to a convert marble leader card to the
     * correspondent {@link WhiteMarbleEffect} instance of the Strategy pattern.
     * @param type of the convert marble.
     * @return the instance of the Strategy pattern used to convert white marbles.
     */
    private WhiteMarbleEffect convertTypeToMarbleEffect(ResourceType type) {
        if(type == ResourceType.coin) return new Coin();
        if(type == ResourceType.servant) return new Servant();
        if(type == ResourceType.shield) return new Shield();
        if(type == ResourceType.stone) return new Stone();
        else return null;
    }

    /**
     * Performs the positioning of the resources obtained from the marbles into the {@link Warehouse} as
     * indicated in the previous phases of the action. If the positioning action fails, the resource is
     * added to the pending resources.
     * @param playerWarehouse where to position the resources.
     */
    private void handlePositioning(Warehouse playerWarehouse) {
        for(int i = 0; i < whereToPutResources.size(); ) {
            try {
                performPositioning(playerWarehouse, i);
            } catch (DepotOutOfBoundsException | DepotNotFoundException | IncompatibleResourceTypeException e) {
                pendingResources.add(whereToPutResources.get(i).getKey());
            } finally {
                i++;
            }
        }
    }

    /**
     * Method used to handle the positioning of the pending resources when the current player wrongly performed
     * a first attempt to position them. If he makes any mistake, the remaining resources are automatically discarded
     * and the other players move by the correspondent number of positions on their faith track.
     * @param turn in which the action is performed.
     * @param pairList the list containing, for each resource, the warehouse section where to position it.
     */
    public void handlePositioning(Turn turn, List<Pair<ResourceType,MarbleDestination>> pairList) {
        List<ResourceType> discardedResourcesList = new ArrayList<>();
        Warehouse playerWarehouse = turn.getPlayer().getPersonalBoard().getWarehouse();
        whereToPutResources = pairList;
        for(int i = 0; i < whereToPutResources.size(); ) {
            try {
                performPositioning(playerWarehouse, i);
            } catch (DepotOutOfBoundsException | DepotNotFoundException | IncompatibleResourceTypeException e) {
                discardedResources++;
                discardedResourcesList.add(whereToPutResources.get(i).getKey());
            } finally {
                i++;
            }
        }
        pendingResources.clear();
        turn.normalActionDone();
        turn.getGame().notifyUpdate(new PositioningUpdate(turn.getPlayer().getUser(),
                turn.getPlayer().getReducedPersonalBoard(), discardedResourcesList));
        concludeTurnPhase(turn);
    }

    /**
     * Checks whether the player has correctly indicated all the resources to put when he has to some resources
     * to settle.
     * @param resourcesToPut indicated by the client.
     * @return the check validity.
     */
    public boolean checkPendingResourcesPositioning(List<ResourceType> resourcesToPut) {
        return resourcesToPut.containsAll(pendingResources) && pendingResources.containsAll(resourcesToPut);
    }

    /**
     * Moves the current player's faith marker by the number of faith points obtained during the action.
     * @param turn in which the action is performed.
     */
    private void moveCurrPlayerMarker(Turn turn) {
        turn.getPlayer().getPersonalBoard().moveMarker(turn.getPlayer(), faith);
    }

    /**
     * Concludes the action moving the other players on their faith track by the number of resources discarded by
     * the current player during this action.
     * @param turn in which the action is performed.
     */
    public void concludeTurnPhase(Turn turn) {
        turn.getGame().moveOtherPlayers(turn.getPlayer(), discardedResources);
    }


    /**
     * Performs the positioning of a resource type in the player's Warehouse as indicated in the previous phases
     * of the action.
     * @param playerWarehouse where to put the resources.
     * @param i the index of the resource to place in whereToPutResources list.
     * @throws DepotOutOfBoundsException if a resource is put in a full depot.
     * @throws IncompatibleResourceTypeException if the type of a resource is incompatible to the current configuration of the warehouse.
     * @throws DepotNotFoundException if a resource is added to a depot that not exists in player's warehouse.
     */
    private void performPositioning(Warehouse playerWarehouse, int i) throws DepotOutOfBoundsException, IncompatibleResourceTypeException, DepotNotFoundException {
        switch(whereToPutResources.get(i).getValue()) {
            case DEPOT1:
                    playerWarehouse.addResourcesToDepot(1, whereToPutResources.get(i).getKey(), 1);
                    break;
            case DEPOT2:
                    playerWarehouse.addResourcesToDepot(2, whereToPutResources.get(i).getKey(), 1);
                    break;
            case DEPOT3:
                    playerWarehouse.addResourcesToDepot(3, whereToPutResources.get(i).getKey(), 1);
                    break;
            case EXTRA:
                    playerWarehouse.addResourcesToExtraDepot(whereToPutResources.get(i).getKey(),1);
                    break;
            case DISCARD: discardedResources++;
                break;
            default:
        }
    }

    /**
     * Filters all the resources obtained from the marbles to get only the resources that the player
     * wants to keep.
     * @return the resources that the player wants to keep.
     */
    private List<ResourceType> getEarnedResources() {
        return whereToPutResources.stream().filter(x -> x.getValue()!=MarbleDestination.DISCARD).
                map(Pair::getKey).collect(Collectors.toList());
    }

    /**
     * Checks whether the client has correctly indicated how to convert each white marble.
     * Client has to indicate how to convert each white marble only if he has two convert marble leader effects active.
     * @param turn in which the action is performed.
     * @param whiteEffects the list containing, for each white marble, the index of the convert marble leader card to active.
     * @param requestedMarbles the list of the marbles requested by the client.
     * @return the check validity.
     */
    public boolean checkValidWhiteEffects(Turn turn, List<Integer> whiteEffects, List<ReducedMarble> requestedMarbles)
    {
        List<ResourceType> whiteMarbleEffects = turn.getPlayer().getActiveEffects().stream().filter(x -> x.getEffect()==Effect.CMARBLE).
                map(LeaderEffect::getType).collect(Collectors.toList());
        if(whiteMarbleEffects.size()==2)
        {
            if(whiteEffects.size()!=requestedMarbles.stream().filter(x -> x.getColorMarble() == ColorMarble.WHITE).count())
                return false;
        }
        return true;
    }

    public List<Pair<ResourceType, MarbleDestination>> getWhereToPutResources() {
        return whereToPutResources;
    }

    public int getFaith() {
        return faith;
    }

    public List<ResourceType> getPendingResources() {
        return pendingResources;
    }
}
