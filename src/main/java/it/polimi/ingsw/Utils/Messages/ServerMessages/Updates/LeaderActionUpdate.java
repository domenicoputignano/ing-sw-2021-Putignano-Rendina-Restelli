package it.polimi.ingsw.Utils.Messages.ServerMessages.Updates;

import it.polimi.ingsw.Commons.LeaderCard;
import it.polimi.ingsw.Network.Client;

public class LeaderActionUpdate extends UpdateMessage {
    private LeaderCard leaderCard;
    private int index;

    @Override
    public void handleUpdateMessage(Client client) {

    }
}
