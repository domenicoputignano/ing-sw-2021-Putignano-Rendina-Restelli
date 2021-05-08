package it.polimi.ingsw.Utils.Messages.ServerMessages.Updates;

import it.polimi.ingsw.Client.ReducedPersonalBoard;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Model.ResourceType;
import it.polimi.ingsw.Network.Client;

import java.util.Map;

public class FaithMarkerUpdate extends UpdateMessage{
    private final int points;
    private final User triggeringUser;
    public FaithMarkerUpdate(User user, ReducedPersonalBoard reducedPersonalBoard,User triggeringUser, int points)
    {
        this.user = user;
        this.userPersonalBoard = reducedPersonalBoard;
        this.triggeringUser = triggeringUser;
        this.points = points;
    }

    @Override
    public void handleUpdateMessage(Client client) {

    }
}
