package it.polimi.ingsw.Client.clientstates;

import it.polimi.ingsw.Utils.Messages.ClientMessages.LeaderActionMessage;

public abstract class AbstractLeaderAction extends AbstractClientState {

    protected LeaderActionMessage messageToSend = new LeaderActionMessage();

    public LeaderActionMessage getMessageToSend() {
        return messageToSend;
    }
}
