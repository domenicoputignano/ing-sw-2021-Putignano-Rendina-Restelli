package it.polimi.ingsw.Client.clientstates;

import it.polimi.ingsw.Client.Checker;
import it.polimi.ingsw.Client.ReducedPersonalBoard;
import it.polimi.ingsw.Client.ReducedPlayer;
import it.polimi.ingsw.Commons.CardType;
import it.polimi.ingsw.Commons.Deck;
import it.polimi.ingsw.Commons.Effect;
import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Utils.Messages.ClientMessages.BuyDevCardMessage;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractBuyDevCard extends AbstractClientState{
    protected BuyDevCardMessage message = new BuyDevCardMessage();

    protected boolean checkDeckIsNotEmpty() {
        return takeDeckFromCardType(message.getType()).getSize()>0;
    }

    protected boolean checkCostRequiredCardType(Deck requiredDeck) {
        ReducedPlayer player = client.getGame().getCurrPlayer();
        Map<ResourceType,Integer> actualCost =  new EnumMap<ResourceType, Integer>(requiredDeck.getTop().getCost());

        // Apply sales effects if present
        player.getCompatibleLeaderEffect(Effect.SALES).forEach(x ->
                actualCost.put(x.getLeaderEffect().getType(), actualCost.get(x.getLeaderEffect().getType())-1));

        return Checker.checkResources(actualCost, player.getPersonalBoard());
    }

    protected Deck takeDeckFromCardType(CardType choice) {
        return client.getGame().getDecks().stream().filter(x -> x.getCardType().equals(message.getType())).
                collect(Collectors.toList()).get(0);
    }

}
