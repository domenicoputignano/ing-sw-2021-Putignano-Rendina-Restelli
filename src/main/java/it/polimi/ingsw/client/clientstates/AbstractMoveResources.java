package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.commons.Effect;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.clientMessages.MoveResourcesMessage;

/**
 * This class represents the generic ui state that is reached when the clients wants to move the resources in his depots.
 */
public abstract class AbstractMoveResources extends AbstractClientState {

    /**
     * The message to send to the server containing infos on how to perform the move action.
     */
    protected MoveResourcesMessage messageToSend = new MoveResourcesMessage();

    public AbstractMoveResources(Client client) {
        super(client);
    }

    /**
     * Utility method used to get the number of active extra depots.
     * @return the number of active extra depots.
     */
    public int getNumOfExtraDepots() {
        return client.getGame().getPlayer(client.getUser()).getCompatibleLeaderEffect(Effect.EXTRADEPOT).size();
    }

}
