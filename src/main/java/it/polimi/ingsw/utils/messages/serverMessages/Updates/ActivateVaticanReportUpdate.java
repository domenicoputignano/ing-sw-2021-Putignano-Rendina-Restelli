package it.polimi.ingsw.utils.messages.serverMessages.Updates;


import it.polimi.ingsw.client.reducedmodel.ReducedPersonalBoard;
import it.polimi.ingsw.commons.StateFavorTiles;
import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.network.Client;

/**
 * Class consisting an update concerning {@link it.polimi.ingsw.model.gameEvents.ActivateVaticanReportEvent}.
 */
public class ActivateVaticanReportUpdate extends UpdateMessage {

    /**
     * User that caused activation of Vatican Report by performing an action
     */
    private final User triggeringUser;

    /**
     * Resulting tile state corresponding to vatican section on which a vatican report
     * has been activated.
     */
    private final StateFavorTiles state;

    private final int section;

    public ActivateVaticanReportUpdate(User user, ReducedPersonalBoard reducedPersonalBoard, User triggeringUser, StateFavorTiles state, int section) {
        this.user = user;
        this.userPersonalBoard = reducedPersonalBoard;
        this.triggeringUser = triggeringUser;
        this.state = state;
        this.section = section;
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

    public User getTriggeringUser() {
        return triggeringUser;
    }

    public StateFavorTiles getState() {
        return state;
    }

    public int getSection() {
        return section;
    }
}
