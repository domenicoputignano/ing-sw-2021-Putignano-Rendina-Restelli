package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.clientMessages.ResourceChoiceMessage;

/**
 * This class represents the generic ui state that is reached when the client has to
 * choose the initial resources. This state is reached only in a Multiplayer Mode game
 * by second, third and fourth players.
 */
public abstract class AbstractInitialResourceChoice extends AbstractClientState {

    /**
     * The message to send to the server containing the resources chosen and where to put them.
     */
    protected ResourceChoiceMessage messageToSend;
    public AbstractInitialResourceChoice(Client client) {
        super(client);
    }

}
