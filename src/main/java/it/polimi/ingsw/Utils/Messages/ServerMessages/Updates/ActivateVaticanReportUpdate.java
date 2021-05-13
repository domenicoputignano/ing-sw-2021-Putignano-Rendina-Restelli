package it.polimi.ingsw.Utils.Messages.ServerMessages.Updates;


import it.polimi.ingsw.Client.ReducedPersonalBoard;
import it.polimi.ingsw.Commons.StateFavorTiles;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Network.Client;

public class ActivateVaticanReportUpdate extends UpdateMessage {

    //User that caused activation of Vatican Report by performing an action
    private User triggeringUser;

    //resulting state of tile related to a specific Player that has to match with the destination of this messageToSend
    private StateFavorTiles state;

    private int section;

    public ActivateVaticanReportUpdate(User user, ReducedPersonalBoard reducedPersonalBoard, User triggeringUser, StateFavorTiles state, int section) {
        this.user = user;
        this.userPersonalBoard = reducedPersonalBoard;
        this.triggeringUser = triggeringUser;
        this.state = state;
        this.section = section;
    }

    @Override
    public void handleMessage(Client client) {

    }
}
