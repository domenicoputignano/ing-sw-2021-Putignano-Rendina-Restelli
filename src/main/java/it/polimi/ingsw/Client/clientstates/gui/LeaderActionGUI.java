package it.polimi.ingsw.Client.clientstates.gui;

import it.polimi.ingsw.Client.clientstates.AbstractLeaderAction;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ClientMessages.LeaderActionMessage;

public class LeaderActionGUI extends AbstractLeaderAction {

    public LeaderActionGUI(Client client) {
        super(client);
    }

    public void setLeaderAction(boolean toDiscard) {
        messageToSend.setToDiscard(toDiscard);
    }

    public void setLeaderIndex(int leaderIndex) {
        messageToSend.setIndex(leaderIndex);
    }

    @Override
    public void manageUserInteraction() {
        client.sendMessage(messageToSend);
    }
}
