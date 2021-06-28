package it.polimi.ingsw.utils.messages.serverMessages.Errors;

import it.polimi.ingsw.client.CliStatesController;
import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.network.Client;

public class InvalidMessageError extends ErrorMessage {

    public InvalidMessageError(User triggeringUser) {
        super(triggeringUser);
    }

    @Override
    public void handleMessage(Client handler) {
        if(handler.getUI().isReceiverAction(triggeringUser)) {
            handler.getUI().renderError("Invalid message!");
            CliStatesController.updateCliState(this, handler.getUI());
        }
    }
}
