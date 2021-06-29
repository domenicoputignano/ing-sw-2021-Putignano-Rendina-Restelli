package it.polimi.ingsw.utils.messages.clientMessages;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.network.RemoteView;
import it.polimi.ingsw.utils.MoveActionInterface;

/**
 * Class containing information regarding a move action.
 */
public class MoveResourcesMessage implements TurnControllerHandleable {

    private MoveActionInterface moveAction;

    public MoveActionInterface getMoveAction() {
        return moveAction;
    }

    public void setMoveAction(MoveActionInterface moveAction) {
        this.moveAction = moveAction;
    }

    /**
     * Returns if the message is valid according to {@link MoveActionInterface} implementation.
     */
    public boolean isValidMessage() {
        return moveAction.isValidMove();
    }

    /**
     * Calls right method of turn controller in order to process the message itself and perform the action required by player.
     * @param turnController turn controller instance that process the message.
     * @param sender remote view that forwards the message.
     */
    public void handleMessage(TurnController turnController, RemoteView sender) {
        turnController.handleMoveMessage(this,sender);
    }

    /**
     * This method is called in order to forward message itself to turn controller.
     * @param gameController game controller instance that will process the message.
     * @param sender remote view that forwards the message.
     */
    public void handleMessage(GameController gameController, RemoteView sender) {
        handleMessage(gameController.getTurnController(), sender);
    }

}
