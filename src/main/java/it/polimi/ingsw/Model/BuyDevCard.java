package it.polimi.ingsw.Model;

import it.polimi.ingsw.Commons.*;
import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Utils.Messages.ClientMessages.*;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.BuyDevCardPerformedUpdate;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class BuyDevCard implements AbstractTurnPhase {
    private final Logger LOGGER = Logger.getLogger(BuyDevCard.class.getName());

    /**
     * This map represents the cost a development card
     */
    private Map<ResourceType, Integer> actualCost = new EnumMap<>(ResourceType.class);


    /**
     * Main method of the class used to perform the action in the game. It retrieves chosen card from deck, calculate
     * resources required to buy it and calls methods from PaymentHandler class to make consistency check and perform
     * payment.
     *
     * @see PaymentHandler for further details
     *
     * @throws InvalidActionException if the normal action has already been done for this turn.
     * @throws PaymentErrorException if the payment of the resources needed by the card failed.
     * @throws ResourceMismatchException if the player did a mistake while indicating where to take resources to buy the card.
     * @throws NotEnoughResourcesException if the player doesn't have enough resources to buy the card.
     */
    public void buyDevCard(Turn turn, BuyDevCardMessage message) throws InvalidActionException, PaymentErrorException, ResourceMismatchException, NotEnoughResourcesException {
        if(turn.isDoneNormalAction())
            throw new InvalidActionException();
        Deck requiredDeck = turn.getGame().searchDeck(message.getType());
        if(requiredDeck != null) {
            actualCost = new EnumMap<>(requiredDeck.getTop().getCost());
            List<LeaderEffect> sales = turn.getPlayer().getActiveEffects().stream().filter(x -> x.getEffect() == Effect.SALES).collect(Collectors.toList());
            for (LeaderEffect effect : sales) {
                if (actualCost.get(effect.getType()) > 0) {
                    actualCost.put(effect.getType(), actualCost.get(effect.getType()) - 1);
                }
            }
            if (turn.getPlayer().getPersonalBoard().getWarehouse().checkResources(actualCost)) {
                if(PaymentHandler.checkCostCoherence(message.getHowToTakeResources(),actualCost)) {
                    Warehouse playerWarehouse = turn.getPlayer().getPersonalBoard().getWarehouse();
                    try {
                        PaymentHandler.performPayment(playerWarehouse, message.getHowToTakeResources(), turn);
                        DevelopmentCard drawnCard = requiredDeck.draw();
                        performPurchasingCard(drawnCard, turn.getPlayer().getPersonalBoard(), message.getDestinationSlot());
                        turn.normalActionDone();
                        turn.getGame().notifyUpdate(new BuyDevCardPerformedUpdate(turn.getPlayer().getUser(),
                                turn.getPlayer().getReducedPersonalBoard(),
                                turn.getGame().getDecks(),drawnCard));
                    } catch (DepotOutOfBoundsException | DepotNotFoundException | StrongboxOutOfBoundException e) {
                        LOGGER.log(Level.SEVERE, "Critical error detected: exception not expected thrown! ");
                    }
                } else throw new ResourceMismatchException();
            } else throw new NotEnoughResourcesException();
        }
    }


    /**
     * Put the card that has been just bought in the slot according to player indication
     * @param developmentCard card bought by player
     * @param personalBoard game board of player performing this action
     * @param destinationSlot slot that will hold the card
     */
    private void performPurchasingCard(DevelopmentCard developmentCard, PersonalBoard personalBoard, int destinationSlot) {
        personalBoard.putCardOnTop(developmentCard,destinationSlot);
    }

}
