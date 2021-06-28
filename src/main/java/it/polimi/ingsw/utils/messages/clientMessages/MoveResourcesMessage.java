package it.polimi.ingsw.utils.messages.clientMessages;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.network.RemoteView;
import it.polimi.ingsw.utils.MoveActionInterface;

public class MoveResourcesMessage implements TurnControllerHandleable {

    MoveActionInterface moveAction;

    public MoveActionInterface getMoveAction() {
        return moveAction;
    }

    public void setMoveAction(MoveActionInterface moveAction) {
        this.moveAction = moveAction;
    }

    public boolean isValidMessage() {
        return moveAction.isValidMove();
    }


    public void handleMessage(TurnController turnController, RemoteView sender) {
        turnController.handleMoveMessage(this,sender);
    }

    public void handleMessage(GameController gameController, RemoteView sender) {
        handleMessage(gameController.getTurnController(), sender);
    }

}
