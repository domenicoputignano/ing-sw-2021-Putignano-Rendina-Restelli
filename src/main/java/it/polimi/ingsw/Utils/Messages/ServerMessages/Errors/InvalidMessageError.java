package it.polimi.ingsw.Utils.Messages.ServerMessages.Errors;

import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Network.Client;

public class InvalidMessageError extends ErrorMessage {

    public InvalidMessageError(User triggeringUser) {
        super(triggeringUser);
    }

    @Override
    public void handleMessage(Client handler) {
        if(handler.getUI().isReceiverAction(triggeringUser)) {
            handler.getUI().renderError("Invalid message!");
        }
    }
}
