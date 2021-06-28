package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.clientMessages.NumOfPlayerChoiceMessage;

public abstract class AbstractNumOfPlayersChoice extends AbstractClientState {

    protected NumOfPlayerChoiceMessage messageToSend;

    protected AbstractNumOfPlayersChoice(Client client) {
        super(client);
    }

}
