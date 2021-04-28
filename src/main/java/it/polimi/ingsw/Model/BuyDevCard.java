package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.DepotNotFoundException;
import it.polimi.ingsw.Exceptions.DepotOutOfBoundsException;
import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Exceptions.StrongboxOutOfBoundException;
import it.polimi.ingsw.Model.Card.Deck;
import it.polimi.ingsw.Model.Card.DevelopmentCard;
import it.polimi.ingsw.Model.Card.Effect;
import it.polimi.ingsw.Model.Card.LeaderEffect;
import it.polimi.ingsw.Utils.Messages.BuyDevCardMessage;
import java.util.*;
import java.util.stream.Collectors;

public class BuyDevCard implements AbstractTurnPhase {


    private Map<ResourceType, Integer> actualCost = new EnumMap<ResourceType, Integer>(ResourceType.class);


    public void buyDevCard(Turn turn, BuyDevCardMessage message) throws InvalidActionException {
        if(turn.isDoneNormalAction())
            throw new InvalidActionException();
        Deck requiredDeck = turn.getGame().searchDeck(message.getType());
        if(requiredDeck != null) {
            actualCost = new EnumMap<ResourceType, Integer>(requiredDeck.getTop().getCost());
            List<LeaderEffect> sales = turn.getPlayer().getActiveEffects().stream().filter(x -> x.getEffect() == Effect.SALES).collect(Collectors.toList());
            for (LeaderEffect effect : sales) {
                if (actualCost.get(effect.getType()) > 0) {
                    //actual effect perform -- need to update if FA editing parameters is chosen
                    actualCost.put(effect.getType(), actualCost.get(effect.getType()) - 1);
                }
            }
            if (turn.getPlayer().getPersonalBoard().getWarehouse().checkResources(actualCost)) {
                Warehouse playerWarehouse = turn.getPlayer().getPersonalBoard().getWarehouse();
                try {
                    PaymentHandler.performPayment(playerWarehouse, message.getHowToTakeResources(), turn);
                    performPurchasingCard(requiredDeck.draw(), turn.getPlayer().getPersonalBoard(), message.getDestinationSlot());
                    turn.normalActionDone();
                } catch (DepotOutOfBoundsException e) {
                    //TODO HANDLEERROR (DEPOTOUTOFBOUND)
                } catch (DepotNotFoundException e) {
                    //TODO HANDLEERROR (DEPOTNOTFOUND)
                } catch (StrongboxOutOfBoundException e) {
                    //TODO HANDLEERROR (STRONGBOXOUTOFBOUND)
                }
            }
        }
        else {
                /*settare il model in uno stato di errore e inviare al client gli errori relativi alle mappe corrispondenti*/
        }

    }

    private void performPurchasingCard(DevelopmentCard developmentCard, PersonalBoard personalBoard, int destinationSlot) {
        personalBoard.putCardOnTop(developmentCard,destinationSlot);
    }

}
