package it.polimi.ingsw.utils.messages.clientMessages;


import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.network.RemoteView;

/**
 * Interface that encapsulates all messages referred to the game.
 */
public interface GameControllerHandleable extends ClientMessage {
    /**
     * Method called at the moment of message receiving to know how to process it.
     * @param gameController game controller instance that will process the message.
     * @param sender remote view that forwards the message.
     */
    void handleMessage(GameController gameController, RemoteView sender);

    /**
     * Boolean method called to know if the message is valid or not depending on its implementation.
     */
    boolean isValidMessage();
}
