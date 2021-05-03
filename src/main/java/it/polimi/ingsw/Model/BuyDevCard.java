package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Commons.Deck;
import it.polimi.ingsw.Commons.DevelopmentCard;
import it.polimi.ingsw.Commons.Effect;
import it.polimi.ingsw.Commons.LeaderEffect;
import it.polimi.ingsw.Utils.Messages.ClientMessages.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class BuyDevCard implements AbstractTurnPhase {
    private Logger LOGGER = Logger.getLogger(BuyDevCard.class.getName());
    private Map<ResourceType, Integer> actualCost = new EnumMap<ResourceType, Integer>(ResourceType.class);


    public void buyDevCard(Turn turn, BuyDevCardMessage message) throws InvalidActionException, PaymentErrorException {
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
                if(PaymentHandler.checkCostCoherence(message.getHowToTakeResources(),actualCost)) {
                    Warehouse playerWarehouse = turn.getPlayer().getPersonalBoard().getWarehouse();
                    try {
                        PaymentHandler.performPayment(playerWarehouse, message.getHowToTakeResources(), turn);
                        performPurchasingCard(requiredDeck.draw(), turn.getPlayer().getPersonalBoard(), message.getDestinationSlot());
                        turn.normalActionDone();
                    } catch (DepotOutOfBoundsException | DepotNotFoundException | StrongboxOutOfBoundException e) {
                        LOGGER.log(Level.SEVERE, "Critical error detected: exception not expected thrown! ");
                    }
                } else {

                }
            } else {
                /*settare il model in uno stato di errore e inviare al client gli errori relativi alle mappe corrispondenti*/
            }
        } else {

        }

    }

    private void performPurchasingCard(DevelopmentCard developmentCard, PersonalBoard personalBoard, int destinationSlot) {
        personalBoard.putCardOnTop(developmentCard,destinationSlot);
    }

}
