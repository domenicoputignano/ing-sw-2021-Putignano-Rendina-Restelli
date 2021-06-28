package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.clientMessages.UsernameChoiceMessage;

/**
 * This class represents the generic username choice state which is reached by the client
 * at the launch of the game.
 */
public abstract class AbstractUserNameChoice extends AbstractClientState {

    /**
     * The message to send to the server containing the username chosen.
     */
    protected UsernameChoiceMessage messageToSend = new UsernameChoiceMessage();

    protected AbstractUserNameChoice(Client client) {
        super(client);
    }

}
