package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.clientMessages.UsernameChoiceMessage;

public abstract class AbstractUserNameChoice extends AbstractClientState {


    protected UsernameChoiceMessage messageToSend = new UsernameChoiceMessage();

    protected AbstractUserNameChoice(Client client) {
        super(client);
    }

}
