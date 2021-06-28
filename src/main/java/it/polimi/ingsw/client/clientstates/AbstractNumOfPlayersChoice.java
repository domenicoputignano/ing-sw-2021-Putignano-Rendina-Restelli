package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.clientMessages.NumOfPlayerChoiceMessage;

/**
 * This class represents the generic number of players choice state which is reached by the client
 * after the game mode choice.
 */
public abstract class AbstractNumOfPlayersChoice extends AbstractClientState {

    /**
     * The message to send to the server containing the number of players chosen.
     */
    protected NumOfPlayerChoiceMessage messageToSend;

    protected AbstractNumOfPlayersChoice(Client client) {
        super(client);
    }

}
