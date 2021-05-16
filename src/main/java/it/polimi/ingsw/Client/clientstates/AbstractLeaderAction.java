package it.polimi.ingsw.Client.clientstates;

import it.polimi.ingsw.Client.clientstates.cli.LeaderActionCLI;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ClientMessages.LeaderActionMessage;

public abstract class AbstractLeaderAction extends AbstractClientState {

    protected LeaderActionMessage messageToSend = new LeaderActionMessage();

    protected AbstractLeaderAction(Client client) {
        super(client);
    }

    public LeaderActionMessage getMessageToSend() {
        return messageToSend;
    }

    @Override //TODO
    public AbstractClientState getGUIVersion() {
        return null;
    }

    @Override
    public LeaderActionCLI getCLIVersion() {
        return new LeaderActionCLI(client);
    }
}
