package it.polimi.ingsw.utils.messages.serverMessages.Updates;

import it.polimi.ingsw.client.CliStatesController;
import it.polimi.ingsw.client.reducedmodel.ReducedPersonalBoard;
import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.network.Client;

/**
 * Class representing an update sent after a
 */
public class MoveUpdate extends UpdateMessage {

    public MoveUpdate(User user, ReducedPersonalBoard reducedPersonalBoard){
        this.user = user;
        this.userPersonalBoard = reducedPersonalBoard;
    }

    @Override
    public void handleMessage(Client handler) {
        handler.getGame().updatePersonalBoard(this);
        handler.getUI().render(this);
        CliStatesController.updateCliState(this, handler.getUI());
    }
}
