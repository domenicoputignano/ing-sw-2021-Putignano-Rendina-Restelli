package it.polimi.ingsw.Utils.Messages.ServerMessages.Updates;

import it.polimi.ingsw.Client.CliStatesController;
import it.polimi.ingsw.Client.reducedmodel.ReducedMultiPlayerMode;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;

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
