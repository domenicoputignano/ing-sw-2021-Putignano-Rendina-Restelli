package it.polimi.ingsw.utils.messages.serverMessages.Updates;

import it.polimi.ingsw.client.reducedmodel.ReducedPersonalBoard;
import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.network.Client;

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
