package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Model.Card.Effect;
import it.polimi.ingsw.Model.MarketTray.*;
import it.polimi.ingsw.Utils.MarbleDestination;
import it.polimi.ingsw.Utils.Pair;
import it.polimi.ingsw.Utils.TakeResourcesFromMarketMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



public class TakeResourcesFromMarket implements AbstractTurnPhase {

    List<Pair<ResourceType, MarbleDestination>> whereToPutResources = new ArrayList<>();
    List<ResourceType> pendingResources = new ArrayList<>();
    int faith = 0;
    int discardedResources = 0;

    @Override
    public void takeResourcesFromMarket(Turn turn, TakeResourcesFromMarketMessage takeResourcesFromMarketMessage) throws InvalidActionException {
        if(turn.isDoneNormalAction())
            throw new InvalidActionException();
        convertWhiteMarbles(turn,takeResourcesFromMarketMessage.getWhereToPutMarbles(),takeResourcesFromMarketMessage.getWhiteEffects());
        convertMarblesToResources(takeResourcesFromMarketMessage.getWhereToPutMarbles());
        handlePositioning(turn.getPlayer().getPersonalBoard().getWarehouse());
        /*TODO: togliere gli effetti dalle whiteMarbles*/
        //TODO : if(pendingResources.size()>0) HANDLEERROR(Risorse non correttamente posizionate)

        turn.getGame().getMarketTray().clearWhiteMarbleEffect();

    }

    private void convertWhiteMarbles(Turn turn, List<Pair<Marble, MarbleDestination>> pairList, List<Integer> whiteEffects) {
        List<ResourceType> whiteMarbleEffects = turn.getPlayer().getActiveEffects().stream().filter(x -> x.getEffect()==Effect.CMARBLE).
                map(x -> x.getType()).collect(Collectors.toList());
        List<Marble> marbles = pairList.stream().map(Pair::getKey).collect(Collectors.toList());
        int i = 0;
        for(Marble m : marbles) {
            if(m.getColor()==Color.WHITE) {
                if(whiteMarbleEffects.size()==1) {
                    WhiteMarble y = (WhiteMarble) m;
                    y.setEffect(convertTypeToMarbleEffect(whiteMarbleEffects.get(0)));
                } else if(whiteMarbleEffects.size() == 2) {
                    WhiteMarble y = (WhiteMarble) m;
                    y.setEffect(convertTypeToMarbleEffect(whiteMarbleEffects.get(whiteEffects.get(i))));
                    i++;
                }
            }
        }
    }

    private void convertMarblesToResources(List<Pair<Marble,MarbleDestination>> marbles) {
        for(Pair<Marble,MarbleDestination> p : marbles) {
            if (p.getKey().getColor() == Color.RED)
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
    public void handlePositioning(Warehouse warehouse, List<Pair<ResourceType,MarbleDestination>> pairList) {
        for(int i = 0; i < pairList.size(); ) {
            try {
                performPositioning(warehouse, i);
            } catch (DepotOutOfBoundsException | DepotNotFoundException | IncompatibleResourceTypeException e) {
                //TODO: potenzialmente da togliere
                pendingResources.remove(pairList.get(i).getKey());
                discardedResources++;
            } finally {
                i++;
            }
        }
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
            case DEPOT2:
                    playerWarehouse.addResourcesToDepot(2, whereToPutResources.get(i).getKey(), 1);
            case DEPOT3:
                    playerWarehouse.addResourcesToDepot(3, whereToPutResources.get(i).getKey(), 1);
            case EXTRA:
                    playerWarehouse.addResourcesToExtraDepot(whereToPutResources.get(i).getKey(),1);
            case DISCARD: discardedResources++;
            default:
        }
    }






    public List<Pair<ResourceType, MarbleDestination>> getWhereToPutResources() {
        return whereToPutResources;
    }

    public int getFaith() {
        return faith;
    }
}
