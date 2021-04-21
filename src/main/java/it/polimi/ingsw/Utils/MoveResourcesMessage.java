package it.polimi.ingsw.Utils;

public class MoveResourcesMessage {

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
}
