package it.polimi.ingsw.utils.messages.serverMessages.Updates;


import it.polimi.ingsw.client.reducedmodel.ReducedPersonalBoard;
import it.polimi.ingsw.commons.StateFavorTiles;
import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.network.Client;

public class ActivateVaticanReportUpdate extends UpdateMessage {

    //User that caused activation of Vatican Report by performing an action
    private final User triggeringUser;

    //resulting state of tile related to a specific Player that has to match with the destination of this messageToSend
    private final StateFavorTiles state;

    private final int section;

    public ActivateVaticanReportUpdate(User user, ReducedPersonalBoard reducedPersonalBoard, User triggeringUser, StateFavorTiles state, int section) {
        this.user = user;
        this.userPersonalBoard = reducedPersonalBoard;
        this.triggeringUser = triggeringUser;
        this.state = state;
        this.section = section;
    }

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
