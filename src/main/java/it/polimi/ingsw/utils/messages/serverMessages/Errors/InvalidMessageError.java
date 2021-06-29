package it.polimi.ingsw.utils.messages.serverMessages.Errors;

import it.polimi.ingsw.client.CliStatesController;
import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.network.Client;

/**
 * Class representing an error that occurred when a client sends a badly formatted message.
 * Server replies with an instance of this class in order to notify it.
 */
public class InvalidMessageError extends ErrorMessage {

    public InvalidMessageError(User triggeringUser) {
        super(triggeringUser);
    }

    /**
     * Method called by {@link Client} to display the error.
     * @param handler Client instance that has to handle the message.
     */
    @Override
    public void handleMessage(Client handler) {
        if(handler.getUI().isReceiverAction(triggeringUser)) {
            handler.getUI().renderError("Invalid message!");
            CliStatesController.updateCliState(this, handler.getUI());
        }
    }
}
