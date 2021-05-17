package it.polimi.ingsw.Model;

import it.polimi.ingsw.Client.ReducedMarble;
import it.polimi.ingsw.Commons.ColorMarble;
import it.polimi.ingsw.Commons.LeaderEffect;
import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Commons.Effect;
import it.polimi.ingsw.Model.MarketTray.*;
import it.polimi.ingsw.Utils.MarbleDestination;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.FaithMarkerUpdate;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.PositioningUpdate;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.TakeResourcesFromMarketUpdate;
import it.polimi.ingsw.Utils.Pair;
import it.polimi.ingsw.Utils.Messages.ClientMessages.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



public class TakeResourcesFromMarket implements AbstractTurnPhase {

    private List<Pair<ResourceType, MarbleDestination>> whereToPutResources = new ArrayList<>();
    private List<ResourceType> pendingResources = new ArrayList<>();
    private int faith = 0;
    private int discardedResources = 0;

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
            turn.normalActionDone();
            moveCurrPlayerMarker(turn);
            if(pendingResources.size()>0)
                throw new NeedPositioningException(pendingResources);
            else {
                turn.getGame().notifyUpdate(new TakeResourcesFromMarketUpdate(turn.getPlayer().getUser(),
                        turn.getPlayer().getReducedPersonalBoard(),
                        turn.getGame().getMarketTray().getReducedVersion(),
                        getEarnedResources()));
                concludeTurnPhase(turn);
            }
        } else throw new WhiteEffectMismatchException();
    }

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
            } catch (NoSuchResourceTypeException e) {}
        }
    }

    private WhiteMarbleEffect convertTypeToMarbleEffect(ResourceType type) {
        if(type == ResourceType.coin) return new Coin();
        if(type == ResourceType.servant) return new Servant();
        if(type == ResourceType.shield) return new Shield();
        if(type == ResourceType.stone) return new Stone();
        else return null;
    }

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


    //metodo per gestire la seconda possibilità data al giocatore di posizionare le risorse nei depot
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
        turn.getGame().notifyUpdate(new PositioningUpdate(turn.getPlayer().getUser(),
                turn.getPlayer().getReducedPersonalBoard(), discardedResourcesList));
        concludeTurnPhase(turn);
    }

    public boolean checkPendingResourcesPositioning(List<ResourceType> resourcesToPut)
    {
        return resourcesToPut.containsAll(pendingResources) && pendingResources.containsAll(resourcesToPut);
    }

    private void moveCurrPlayerMarker(Turn turn)
    {
        turn.getPlayer().getPersonalBoard().getFaithTrack().moveMarker(faith);
    }

    public void concludeTurnPhase(Turn turn) {
        for(Player  p : turn.getGame().getPlayerList()) {
            if(!p.equals(turn.getPlayer())) {
                p.getPersonalBoard().getFaithTrack().moveMarker(discardedResources);
                turn.getGame().notifyUpdate(new FaithMarkerUpdate(p.getUser(),
                        p.getReducedPersonalBoard(),
                        turn.getPlayer().getUser(),
                        discardedResources));
            }
        }
    }


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

    private List<ResourceType> getEarnedResources()
    {
        return whereToPutResources.stream().map(Pair::getKey).collect(Collectors.toList());
    }

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
