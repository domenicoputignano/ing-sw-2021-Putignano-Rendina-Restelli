package it.polimi.ingsw.Utils.Messages.ServerMessages.Updates;

import it.polimi.ingsw.Client.ReducedPersonalBoard;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Network.Client;

public class InitialLeaderChoiceUpdate implements UpdateMessage {

    private User user;
    private ReducedPersonalBoard reducedPersonalBoard;

    @Override
    public void handleUpdateMessage(Client client) {

    }
}
