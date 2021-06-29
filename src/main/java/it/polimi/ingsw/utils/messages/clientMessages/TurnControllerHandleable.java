package it.polimi.ingsw.utils.messages.clientMessages;

import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.network.RemoteView;

/**
 * Interface that encapsulates all the messages that require an instance of {@link TurnController} to handle them.
 */
public interface TurnControllerHandleable extends GameControllerHandleable {

    /**
     * Method called when it's received a message that requires a turn controller to be handled.
     * @param turnController turn controller instance that process the message.
     * @param sender remote view that forwards the message.
     */
    void handleMessage(TurnController turnController, RemoteView sender);


}
