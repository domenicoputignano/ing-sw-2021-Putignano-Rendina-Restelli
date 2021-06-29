package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.client.reducedmodel.ReducedMarble;
import it.polimi.ingsw.commons.Effect;
import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.clientMessages.TakeResourcesFromMarketMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class represents the generic ui state that is reached when the client chooses to
 * take resources from the {@link it.polimi.ingsw.model.marketTray.MarketTray} during his turn.
 */
public abstract class AbstractTakeResourcesFromMarket extends AbstractClientState {
    /**
     * The message to send to the server containing instructions on how to perform the action.
     */
    protected TakeResourcesFromMarketMessage message = new TakeResourcesFromMarketMessage();
    /**
     * Marbles the player wants to take.
     */
    protected List<ReducedMarble> selectedMarbles = new ArrayList<>();

    public AbstractTakeResourcesFromMarket(Client client) {
        super(client);
    }

    /**
     * Computes the list of selected marbles looking at the Market Tray referring to the choices made by the player.
     */
    protected void computeSelectedMarbles() {
        this.selectedMarbles = client.getGame().getMarketTray().peekMarbles(message.getPlayerChoice(), message.getIndex()-1);
    }

    /**
     * Utility method used to retrieve all the active convert marble effects for the player.
     * @return the list of {@link ResourceType} associated with the available convert marble effects.
     */
    public List<ResourceType> getConvertMarbleActiveEffects(){
        return client.getGame().getCurrPlayer().getCompatibleLeaderEffect(Effect.CMARBLE).stream().
                map(x -> x.getLeaderEffect().getType()).collect(Collectors.toList());
    }

}
