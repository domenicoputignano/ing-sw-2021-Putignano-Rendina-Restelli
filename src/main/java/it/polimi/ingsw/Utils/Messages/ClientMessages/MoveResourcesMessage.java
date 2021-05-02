package it.polimi.ingsw.Utils.Messages.ClientMessages;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.TurnController;
import it.polimi.ingsw.Network.RemoteView;
import it.polimi.ingsw.Utils.MoveActionInterface;

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
