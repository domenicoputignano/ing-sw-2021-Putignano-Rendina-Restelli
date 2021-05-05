package it.polimi.ingsw.Utils.Messages.ServerMessages.Updates;


import it.polimi.ingsw.Commons.StateFavorTiles;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Network.Client;

public class ActivateVaticanReportUpdate extends UpdateMessage {

    //User that caused activation of Vatican Report by performing an action
    private User triggeringUser;

    //resulting state of tile related to a specific Player that has to match with the destination of this message
    private StateFavorTiles state;

    private int section;

    @Override
    public void handleUpdateMessage(Client client) {

    }
}
