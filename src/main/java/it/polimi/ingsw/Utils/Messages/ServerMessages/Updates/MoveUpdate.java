package it.polimi.ingsw.Utils.Messages.ServerMessages.Updates;

import it.polimi.ingsw.Client.ReducedPersonalBoard;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Network.Client;

public class MoveUpdate extends UpdateMessage {

    public MoveUpdate(User user, ReducedPersonalBoard reducedPersonalBoard){
        this.user = user;
        this.userPersonalBoard = reducedPersonalBoard;
    }

    @Override
    public void handleMessage(Client handler) {

    }
}
