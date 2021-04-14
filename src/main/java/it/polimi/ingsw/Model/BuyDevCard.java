package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.DepotNotFoundException;
import it.polimi.ingsw.Exceptions.DepotOutOfBoundsException;
import it.polimi.ingsw.Model.Card.Deck;
import it.polimi.ingsw.Model.Card.Effect;
import it.polimi.ingsw.Model.Card.LeaderEffect;
import it.polimi.ingsw.Utils.BuyDevCardMessage;

import java.util.*;
import java.util.stream.Collectors;

public class BuyDevCard implements AbstractTurnPhase {

    private Map<ResourceType, Integer> actualCost = null;


    public void buyDevCard(Turn turn, BuyDevCardMessage message) {
        Optional<Deck> d = turn.getGame().getDecks().stream().filter(x -> x.getCardType().equals(message.getType())).findFirst();
        if(d.isPresent()) actualCost = new EnumMap<ResourceType, Integer>(d.get().getTop().getCost());
        List<LeaderEffect> sales = turn.getPlayer().getActiveEffects().stream().filter(x -> x.getEffect() == Effect.SALES).collect(Collectors.toList());
        for(LeaderEffect effect : sales) {
            if(actualCost.get(effect.getType()) > 0) {
                //actual effect perform -- need to update if FA editing parameters is chosen
                actualCost.put(effect.getType(),actualCost.get(effect.getType()) - 1);
            }
        }
        if(turn.getPlayer().getPersonalBoard().getWarehouse().checkResources(actualCost)) {
            EnumMap<ResourceType, Integer> toTakeFromDepot = message.getHowToTakeResources().get("Depot").clone();
            EnumMap<ResourceType, Integer> toTakeFromStrongBox = message.getHowToTakeResources().get("Strongbox").clone();
            EnumMap<ResourceType, Integer> toTakeFromExtraDepot = message.getHowToTakeResources().get("ExtraDepot").clone();
            for (ResourceType resourceType : toTakeFromDepot.keySet()) {
                if(!turn.getPlayer().getPersonalBoard().getWarehouse().checkResourceFromNormalDepot(resourceType,toTakeFromDepot.get(resourceType))) {
                    //mettere il model in uno stato di errore e notificare il client che ha sbagliato le occorrenze nei depot
                    //TODO
                }
            }
            for (ResourceType resourceType : toTakeFromStrongBox.keySet()) {
                if(!turn.getPlayer().getPersonalBoard().getWarehouse().checkResourceFromStrongBox(resourceType,toTakeFromStrongBox.get(resourceType))) {
                    //mettere il model in uno stato di errore e notificare il client che ha sbagliato le occorrenze nello strongbox
                    //TODO
                }
            }
            for (ResourceType resourceType : toTakeFromExtraDepot.keySet()) {
                if(!turn.getPlayer().getPersonalBoard().getWarehouse().checkResourceFromExtraDepot(resourceType,toTakeFromExtraDepot.get(resourceType))) {
                    //mettere il model in uno stato di errore e notificare il client che ha sbagliato le occorrenze negli extradepot
                    //TODO
                }
            }

            for (ResourceType resourceType: toTakeFromDepot.keySet()) {
                try {
                    int i = turn.getPlayer().getPersonalBoard().getWarehouse().findNormalDepotByResourceType(resourceType);
                    try {
                        turn.getPlayer().getPersonalBoard().getWarehouse().getNormalDepots()[i].take(toTakeFromDepot.get(resourceType));
                    } catch (DepotOutOfBoundsException e) {
                    }
                } catch (DepotNotFoundException e) {
                }
            }
        }
    }
}
