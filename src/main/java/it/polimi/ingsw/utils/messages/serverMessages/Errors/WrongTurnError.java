package it.polimi.ingsw.utils.messages.serverMessages.Errors;


import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.network.Client;

public class WrongTurnError extends ErrorMessage {

    public WrongTurnError(User triggeringUser) {
        super(triggeringUser);
    }

    @Override
    public void handleMessage(Client handler) {
        if(handler.getUI().isReceiverAction(triggeringUser)){
            handler.getUI().renderError("It's not your turn, please wait..");
        }
    }
}
