package it.polimi.ingsw.Client.clientstates;

import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ClientMessages.UsernameChoiceMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;

public abstract class AbstractUserNameChoice extends AbstractClientState {


    protected UsernameChoiceMessage messageToSend = new UsernameChoiceMessage();

    protected AbstractUserNameChoice(Client client) {
        super(client);
    }

    @Override //TODO
    public AbstractClientState getGUIVersion() {
        return null;
    }

    @Override
    public AbstractClientState getCLIVersion() {
        return null;
    }


}
