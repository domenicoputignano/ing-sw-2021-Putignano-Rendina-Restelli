package it.polimi.ingsw.Utils.Messages.ServerMessages.Updates;

import it.polimi.ingsw.Client.ReducedPersonalBoard;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Network.Client;

public class InitialLeaderChoiceUpdate extends UpdateMessage {

    private int leader1ToDiscard;
    private int leader2ToDiscard;

    @Override
    public void handleUpdateMessage(Client client) {

    }
}
