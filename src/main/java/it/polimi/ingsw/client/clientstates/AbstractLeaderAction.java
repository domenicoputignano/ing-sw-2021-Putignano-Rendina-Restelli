package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.clientMessages.LeaderActionMessage;

public abstract class AbstractLeaderAction extends AbstractClientState {

    protected LeaderActionMessage messageToSend = new LeaderActionMessage();

    protected AbstractLeaderAction(Client client) {
        super(client);
    }

    public LeaderActionMessage getMessageToSend() {
        return messageToSend;
    }

}
