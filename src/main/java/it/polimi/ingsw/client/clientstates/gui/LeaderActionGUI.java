package it.polimi.ingsw.client.clientstates.gui;

import it.polimi.ingsw.client.clientstates.AbstractLeaderAction;
import it.polimi.ingsw.network.Client;

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
