package it.polimi.ingsw.Client.clientstates;

import it.polimi.ingsw.Client.Checker;
import it.polimi.ingsw.Client.ReducedPlayer;
import it.polimi.ingsw.Commons.*;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ClientMessages.BuyDevCardMessage;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractBuyDevCard extends AbstractClientState{
    protected BuyDevCardMessage messageToSend = new BuyDevCardMessage();
    protected Map<ResourceType, Integer> actualCost;

    public AbstractBuyDevCard(Client client) {
        super(client);
    }

    protected boolean deckIsEmpty() {
        return takeDeckFromCardType(messageToSend.getType()).isEmpty();
    }

    protected boolean checkCostRequiredCardType(Map<ResourceType, Integer> cost) {
        ReducedPlayer player = client.getGame().getCurrPlayer();
        return Checker.checkResources(cost, player.getPersonalBoard());
    }

    protected void computeActualCost(Deck requiredDeck) {
        ReducedPlayer player = client.getGame().getCurrPlayer();
        actualCost = new EnumMap<>(requiredDeck.getTop().getCost());

        // Apply sales effects if present
        player.getCompatibleLeaderEffect(Effect.SALES).forEach(x ->
                actualCost.put(x.getLeaderEffect().getType(), actualCost.get(x.getLeaderEffect().getType())-1));
    }

    protected boolean canBuyCardOfLevel(int level) {
        return client.getGame().getCurrPlayer().getPersonalBoard().canBuyCardOfLevel(level);
    }

    protected boolean canActivateCardOnThisSlot(int slotIndex){
        Slot slot = client.getGame().getCurrPlayer().getPersonalBoard().getSlot(slotIndex);
        int level = messageToSend.getType().getLevel();
        return level == 1 ? slot.getNumOfStackedCards() == 0 : slot.peekTopCard().getType().getLevel() == (level - 1);
    }

    protected Deck takeDeckFromCardType(CardType choice) {
        return client.getGame().getDecks().stream().filter(x -> x.getCardType().equals(choice)).
                collect(Collectors.toList()).get(0);
    }

}
