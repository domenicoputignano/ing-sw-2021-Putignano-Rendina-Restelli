package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.DepotNotFoundException;
import it.polimi.ingsw.Exceptions.DepotOutOfBoundsException;
import it.polimi.ingsw.Exceptions.StrongboxOutOfBoundException;
import it.polimi.ingsw.Model.Card.Deck;
import it.polimi.ingsw.Model.Card.DevelopmentCard;
import it.polimi.ingsw.Model.Card.Effect;
import it.polimi.ingsw.Model.Card.LeaderEffect;
import it.polimi.ingsw.Utils.BuyDevCardMessage;
import java.util.*;
import java.util.stream.Collectors;

public class BuyDevCard implements AbstractTurnPhase, PaymentHandler {

    private Map<ResourceType, Integer> actualCost = null;


    public void buyDevCard(Turn turn, BuyDevCardMessage message) {
        Optional<Deck> d = turn.getGame().getDecks().stream().filter(x -> x.getCardType().equals(message.getType())).findFirst();
        if (d.isPresent()) actualCost = new EnumMap<ResourceType, Integer>(d.get().getTop().getCost());
        List<LeaderEffect> sales = turn.getPlayer().getActiveEffects().stream().filter(x -> x.getEffect() == Effect.SALES).collect(Collectors.toList());
        for (LeaderEffect effect : sales) {
            if (actualCost.get(effect.getType()) > 0) {
                //actual effect perform -- need to update if FA editing parameters is chosen
                actualCost.put(effect.getType(), actualCost.get(effect.getType()) - 1);
            }
        }
        if (turn.getPlayer().getPersonalBoard().getWarehouse().checkResources(actualCost)) {

            EnumMap<ResourceType, Integer> toTakeFromNormalDepot = message.getHowToTakeResources().get("Depot").clone();
            EnumMap<ResourceType, Integer> toTakeFromStrongBox = message.getHowToTakeResources().get("Strongbox").clone();
            EnumMap<ResourceType, Integer> toTakeFromExtraDepot = message.getHowToTakeResources().get("ExtraDepot").clone();

            Warehouse playerWarehouse = turn.getPlayer().getPersonalBoard().getWarehouse();

            boolean normalDepotsCheck = checkResourcesFromNormalDepots(playerWarehouse,toTakeFromNormalDepot);
            boolean extraDepotsCheck = checkResourcesFromExtraDepots(playerWarehouse,toTakeFromExtraDepot);
            boolean strongboxCheck = checkResourcesFromStrongBox(playerWarehouse,toTakeFromStrongBox);
            if(normalDepotsCheck && extraDepotsCheck && strongboxCheck) {
                try {
                    takeResourcesFromNormalDepots(playerWarehouse,toTakeFromNormalDepot);
                } catch (DepotNotFoundException e) {
                    e.printStackTrace();
                } catch (DepotOutOfBoundsException e) {
                    e.printStackTrace();
                }
                try {
                    takeResourcesFromExtraDepots(playerWarehouse, toTakeFromExtraDepot);
                } catch (DepotOutOfBoundsException e) {
                    e.printStackTrace();
                } catch (DepotNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    takeResourcesFromStrongbox(playerWarehouse,toTakeFromStrongBox);
                } catch (StrongboxOutOfBoundException e) {
                    e.printStackTrace();
                }
                performPurchasingCard(d.get().draw(),turn.getPlayer().getPersonalBoard(), message.getDestinationSlot());
            }
            else {
                /*settare il model in uno stato di errore e inviare al client gli errori relativi alle mappe corrispondenti*/
            }
        }
        else {

        }
    }

    private void performPurchasingCard(DevelopmentCard developmentCard, PersonalBoard personalBoard, int destinationSlot) {
        personalBoard.getSlots()[destinationSlot].push(developmentCard);
    }
}
