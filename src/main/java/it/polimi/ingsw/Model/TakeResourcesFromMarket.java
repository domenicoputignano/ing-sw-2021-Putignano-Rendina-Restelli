package it.polimi.ingsw.Model;

import it.polimi.ingsw.Commons.ColorMarble;
import it.polimi.ingsw.Commons.LeaderEffect;
import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Commons.Effect;
import it.polimi.ingsw.Model.MarketTray.*;
import it.polimi.ingsw.Utils.MarbleDestination;
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
    public void takeResourcesFromMarket(Turn turn, TakeResourcesFromMarketMessage takeResourcesFromMarketMessage) throws InvalidActionException {
        if(turn.isDoneNormalAction())
            throw new InvalidActionException();
        if(checkValidWhiteEffects(turn,takeResourcesFromMarketMessage.getWhiteEffects(),takeResourcesFromMarketMessage.getRequestedMarbles())) {
            //perform convert marble effect
            convertWhiteMarbles(turn, takeResourcesFromMarketMessage.getWhereToPutMarbles(), takeResourcesFromMarketMessage.getWhiteEffects());
            //convert a List of Pair<Marble,MarbleDestination> in a List of Pari<ResourceType,MarbleDestination>
            convertMarblesToResources(takeResourcesFromMarketMessage.getWhereToPutMarbles());
            handlePositioning(turn.getPlayer().getPersonalBoard().getWarehouse());
            /*TODO: togliere gli effetti dalle whiteMarbles*/
            //TODO : if(pendingResources.size()>0) UPDATE(Risorse non correttamente posizionate)
            turn.getGame().getMarketTray().clearWhiteMarbleEffect();
        }//TODO: else HANDLEERROR(Lista di conversione white marbles non compatibile)
    }

    private void convertWhiteMarbles(Turn turn, List<Pair<Marble, MarbleDestination>> pairList, List<Integer> whiteEffects) {
        List<ResourceType> whiteMarbleEffects = turn.getPlayer().getActiveEffects().stream().filter(x -> x.getEffect()==Effect.CMARBLE).
                map(x -> x.getType()).collect(Collectors.toList());
                 List<Marble> marbles = pairList.stream().map(Pair::getKey).collect(Collectors.toList());
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

    private void convertMarblesToResources(List<Pair<Marble,MarbleDestination>> marbles) {
        for(Pair<Marble,MarbleDestination> p : marbles) {
            if (p.getKey().getColor() == ColorMarble.RED)
                faith++;
        }
        ResourceType resourceType;
        for(Pair<Marble,MarbleDestination> p : marbles) {
            try {
                resourceType = p.getKey().addResources();
                whereToPutResources.add(new Pair<ResourceType,MarbleDestination>(resourceType,p.getValue()));
            } catch (NoSuchResourceTypeException e) {
            }
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
    public void handlePositioning(Warehouse warehouse, List<Pair<ResourceType,MarbleDestination>> pairList) throws PositioningException {
        boolean foundProblemWhilePositioning = false;
        whereToPutResources = pairList;
        for(int i = 0; i < whereToPutResources.size(); ) {
            try {
                performPositioning(warehouse, i);
            } catch (DepotOutOfBoundsException | DepotNotFoundException | IncompatibleResourceTypeException e) {
                discardedResources++;
                foundProblemWhilePositioning = true;
            } finally {
                i++;
            }
        }
        pendingResources.clear();
        if(foundProblemWhilePositioning) throw new PositioningException();
    }

    public boolean checkPendingResourcesPositioning(List<ResourceType> resourcesToPut)
    {
        return resourcesToPut.containsAll(pendingResources) && pendingResources.containsAll(resourcesToPut);
    }

    public void concludeTurnPhase(Turn turn) {
        for(Player p : turn.getGame().getPlayerList()) {
            if(!p.equals(turn.getPlayer())) {
                p.getPersonalBoard().getFaithTrack().moveMarker(discardedResources);
            } else {
                p.getPersonalBoard().getFaithTrack().moveMarker(faith);
            }
        }
        //TODO notificare il client per la fine dell'azione principale
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

    public boolean checkValidWhiteEffects(Turn turn, List<Integer> whiteEffects, List<Marble> requestedMarbles)
    {
        List<ResourceType> whiteMarbleEffects = turn.getPlayer().getActiveEffects().stream().filter(x -> x.getEffect()==Effect.CMARBLE).
                map(LeaderEffect::getType).collect(Collectors.toList());
        if(whiteMarbleEffects.size()==2)
        {
            if(whiteEffects.size()!=requestedMarbles.stream().filter(x -> x.getColor()== ColorMarble.WHITE).count())
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

    public int getDiscardedResources() {
        return discardedResources;
    }
}
