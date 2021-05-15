package it.polimi.ingsw.Utils.Messages.ServerMessages.Errors;


import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Network.Client;

public class WrongTurnError extends ErrorMessage {

    @Override
    public void handleMessage(Client handler) {

    }

    public WrongTurnError(User triggeringUser) {
        super(triggeringUser);
    }
}
