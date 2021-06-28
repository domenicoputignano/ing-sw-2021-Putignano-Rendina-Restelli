package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.client.Checker;
import it.polimi.ingsw.client.reducedmodel.ReducedPlayer;
import it.polimi.ingsw.commons.*;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.clientMessages.BuyDevCardMessage;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class represents the generic ui state that is reached when the client chooses to
 * buy a Development Card during his turn.
 */
public abstract class AbstractBuyDevCard extends AbstractClientState{
    /**
     * The message to send to the server containing infos on how to perform the action.
     */
    protected BuyDevCardMessage messageToSend = new BuyDevCardMessage();
    /**
     * The actual cost of the card computed after applying sales effects if available.
     */
    protected Map<ResourceType, Integer> actualCost;

    public AbstractBuyDevCard(Client client) {
        super(client);
    }

    /**
     * Utility method to check if the selected deck is empty.
     * @return the check validity.
     */
    protected boolean deckIsEmpty() {
        return takeDeckFromCardType(messageToSend.getType()).isEmpty();
    }

    /**
     * Checks if the player has enough resources to buy the card selected.
     * @param cost the actual cost of the card previously computed.
     * @return the check validity.
     */
    public boolean checkCostRequiredCardType(Map<ResourceType, Integer> cost) {
        ReducedPlayer player = client.getGame().getCurrPlayer();
        return Checker.checkResources(cost, player.getPersonalBoard());
    }

    /**
     * Computes the actual cost of the required card on top of the selected deck.
     * @param requiredDeck the selected deck.
     */
    public void computeActualCost(Deck requiredDeck) {
        computeActualCost(requiredDeck.getTop());
    }

    /**
     * Computes the actual cost of the required card by applying all the sales effects available.
     * @param card the card selected.
     * @return the actual cost computed.
     */
    public Map<ResourceType,Integer> computeActualCost(DevelopmentCard card) {
        ReducedPlayer player = client.getGame().getCurrPlayer();
        actualCost = new EnumMap<>(card.getCost());
        player.getCompatibleLeaderEffect(Effect.SALES).forEach( x -> {
            if(actualCost.get(x.getLeaderEffect().getType()) > 0) {
                actualCost.put(x.getLeaderEffect().getType(), actualCost.get(x.getLeaderEffect().getType())-1);
            }
        });
        return actualCost;
    }

    /**
     * Checks whether the player can buy a card of the specified level according to his slots.
     * @param level the level of the card selected.
     * @return the validity check.
     */
    public boolean canBuyCardOfLevel(int level) {
        return client.getGame().getCurrPlayer().getPersonalBoard().canBuyCardOfLevel(level);
    }

    /**
     * Checks whether the player can activate the selected card on the specified slot.
     * @param slotIndex the slot where the player wants to activate the card.
     * @return the ckeck validity.
     */
    protected boolean canActivateCardOnThisSlot(int slotIndex){
        Slot slot = client.getGame().getCurrPlayer().getPersonalBoard().getSlot(slotIndex);
        int level = messageToSend.getType().getLevel();
        return level == 1 ? slot.getNumOfStackedCards() == 0 : slot.peekTopCard().getType().getLevel() == (level - 1);
    }

    /**
     * Utility method used to retrieve a deck from the given card type.
     * @param choice the card type chosen.
     * @return the deck associated with the given card type.
     */
    protected Deck takeDeckFromCardType(CardType choice) {
        return client.getGame().getDecks().stream().filter(x -> x.getCardType().equals(choice)).
                collect(Collectors.toList()).get(0);
    }

}
