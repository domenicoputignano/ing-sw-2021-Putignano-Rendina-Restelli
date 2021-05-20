package it.polimi.ingsw.Utils.Messages.ServerMessages.Updates;

import it.polimi.ingsw.Client.reducedmodel.ReducedPersonalBoard;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Network.Client;

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
    public void handleMessage(Client client) {
        client.getGame().updatePersonalBoard(this);
        client.getUI().render(this);
    }

    public int getPoints() {
        return points;
    }

    public User getTriggeringUser() {
        return triggeringUser;
    }
}
