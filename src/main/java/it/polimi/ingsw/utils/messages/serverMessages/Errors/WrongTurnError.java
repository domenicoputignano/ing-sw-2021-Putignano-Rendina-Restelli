package it.polimi.ingsw.utils.messages.serverMessages.Errors;


import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.network.Client;

/**
 * Error caused by player who is attempting to do an action without being in turn.
 */
public class WrongTurnError extends ErrorMessage {

    public WrongTurnError(User triggeringUser) {
        super(triggeringUser);
    }

    /**
     * Method called by {@link Client} in order to display error itself.
     * @param handler Client instance that has to handle the message.
     */
    @Override
    public void handleMessage(Client handler) {
        if(handler.getUI().isReceiverAction(triggeringUser)){
            handler.getUI().renderError("It's not your turn, please wait..");
        }
    }
}
