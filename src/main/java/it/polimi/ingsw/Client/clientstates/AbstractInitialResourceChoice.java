package it.polimi.ingsw.Client.clientstates;

import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ClientMessages.ResourceChoiceMessage;

public abstract class AbstractInitialResourceChoice extends AbstractClientState {

    protected ResourceChoiceMessage messageToSend;
    public AbstractInitialResourceChoice(Client client) {
        super(client);
    }

}
