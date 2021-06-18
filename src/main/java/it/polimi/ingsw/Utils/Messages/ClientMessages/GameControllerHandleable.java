package it.polimi.ingsw.Utils.Messages.ClientMessages;


import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.RemoteView;

public interface GameControllerHandleable extends ClientMessage {
    public void handleMessage(GameController gameController, RemoteView sender);
    public boolean isValidMessage();
}
