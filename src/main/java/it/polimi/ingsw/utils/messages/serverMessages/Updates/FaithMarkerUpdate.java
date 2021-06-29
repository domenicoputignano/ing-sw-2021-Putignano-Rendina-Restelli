package it.polimi.ingsw.utils.messages.serverMessages.Updates;

import it.polimi.ingsw.client.reducedmodel.ReducedPersonalBoard;
import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.network.Client;

/**
 * Class representing an update after a {@link it.polimi.ingsw.model.TakeResourcesFromMarket} action
 * to notify a possible progress in faith track.
 */
public class FaithMarkerUpdate extends UpdateMessage{
    /**
     * Points gained by personal board owner
     * @see UpdateMessage
     */
    private final int points;
    /**
     * User who made the action.
     */
    private final User triggeringUser;

    public FaithMarkerUpdate(User user, ReducedPersonalBoard reducedPersonalBoard,User triggeringUser, int points)
    {
        this.user = user;
        this.userPersonalBoard = reducedPersonalBoard;
        this.triggeringUser = triggeringUser;
        this.points = points;
    }

    /**
     * Method called by client in order to update its reduced model and show changes to player
     * @param client {@link Client} instance that manages the update itself.
     */
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
