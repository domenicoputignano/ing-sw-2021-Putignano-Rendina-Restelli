package it.polimi.ingsw.utils.messages.clientMessages;


import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.network.RemoteView;

public interface GameControllerHandleable extends ClientMessage {
    public void handleMessage(GameController gameController, RemoteView sender);
    public boolean isValidMessage();
}
