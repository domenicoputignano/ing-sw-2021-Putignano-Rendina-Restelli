package it.polimi.ingsw.Utils.Messages.ServerMessages.Updates;

import it.polimi.ingsw.Client.ReducedPersonalBoard;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Network.Client;

public class GameSetupUpdate extends UpdateMessage{
    private final int position;

    public GameSetupUpdate(User user, ReducedPersonalBoard reducedPersonalBoard, int position)
    {
        this.user = user;
        this.userPersonalBoard = reducedPersonalBoard;
        this.position = position;
    }

    @Override
    public void handleUpdateMessage(Client client) {

    }
}
