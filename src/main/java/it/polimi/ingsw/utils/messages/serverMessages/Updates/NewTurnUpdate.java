package it.polimi.ingsw.utils.messages.serverMessages.Updates;

import it.polimi.ingsw.client.CliStatesController;
import it.polimi.ingsw.client.reducedmodel.ReducedMultiPlayerMode;
import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.serverMessages.ServerMessage;

/**
 * Class representing an update sent in order to notify all player about who is the one in turn.
 */
public class NewTurnUpdate implements ServerMessage {

    /**
     * User of player in turn.
     */
    private final User currentUser;

    public NewTurnUpdate(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Method called by client in order to update its reduced model and show changes to player
     * @param handler {@link Client} instance that manages the update itself.
     */
    @Override
    public void handleMessage(Client handler) {
        ((ReducedMultiPlayerMode) handler.getGame()).nextTurn(this);
        if(handler.getUI().isReceiverAction(currentUser)) handler.getUI().setNormalActionDone(false);
        CliStatesController.updateCliState(this, handler.getUI());
        handler.getUI().render(this);
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
