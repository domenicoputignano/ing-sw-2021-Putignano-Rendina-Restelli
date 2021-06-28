package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.client.Checker;
import it.polimi.ingsw.client.reducedmodel.ReducedPlayer;
import it.polimi.ingsw.commons.*;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.clientMessages.BuyDevCardMessage;

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

    public boolean checkCostRequiredCardType(Map<ResourceType, Integer> cost) {
        ReducedPlayer player = client.getGame().getCurrPlayer();
        return Checker.checkResources(cost, player.getPersonalBoard());
    }

    public void computeActualCost(Deck requiredDeck) {
        ReducedPlayer player = client.getGame().getCurrPlayer();
        computeActualCost(requiredDeck.getTop());
    }

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


    public boolean canBuyCardOfLevel(int level) {
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
