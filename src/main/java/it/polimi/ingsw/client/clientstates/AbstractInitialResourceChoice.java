package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.clientMessages.ResourceChoiceMessage;

public abstract class AbstractInitialResourceChoice extends AbstractClientState {

    protected ResourceChoiceMessage messageToSend;
    public AbstractInitialResourceChoice(Client client) {
        super(client);
    }

}
