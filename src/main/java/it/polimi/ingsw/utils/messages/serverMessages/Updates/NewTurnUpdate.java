package it.polimi.ingsw.utils.messages.serverMessages.Updates;

import it.polimi.ingsw.client.CliStatesController;
import it.polimi.ingsw.client.reducedmodel.ReducedMultiPlayerMode;
import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.serverMessages.ServerMessage;

public class NewTurnUpdate implements ServerMessage {

    private final User currentUser;

    public NewTurnUpdate(User currentUser) {
        this.currentUser = currentUser;
    }

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
