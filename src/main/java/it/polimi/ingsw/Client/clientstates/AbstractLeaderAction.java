package it.polimi.ingsw.Client.clientstates;

import it.polimi.ingsw.Utils.Messages.ClientMessages.LeaderActionMessage;

public abstract class AbstractLeaderAction extends AbstractClientState {

    LeaderActionMessage messageToSend = new LeaderActionMessage();

}
