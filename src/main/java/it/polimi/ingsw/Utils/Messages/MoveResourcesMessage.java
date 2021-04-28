package it.polimi.ingsw.Utils.Messages;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.TurnController;
import it.polimi.ingsw.Model.Player;
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


    public void handleMessage(TurnController turnController, Player sender) {
        turnController.handleMoveMessage(this);
    }

    public void handleMessage(GameController gameController, Player sender) {
        handleMessage(gameController.getTurnController(), sender);
    }

}
