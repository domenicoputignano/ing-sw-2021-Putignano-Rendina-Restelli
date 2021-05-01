package it.polimi.ingsw.Utils.Messages.ClientMessages;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.TurnController;
import it.polimi.ingsw.Model.Player;

public class LeaderActionMessage  implements TurnControllerHandleable {
    private int index;
    private boolean toDiscard;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isToDiscard() {
        return toDiscard;
    }

    public void setToDiscard(boolean toDiscard) {
        this.toDiscard = toDiscard;
    }

    public boolean isValidMessage()
    {
        return index > 0 && index <= 2;
    }

    public void handleMessage(TurnController turnController, Player sender) {
        turnController.handleLeaderActionMessage(this,sender);
    }

    public void handleMessage(GameController gameController, Player sender) {
        handleMessage(gameController.getTurnController(), sender);
    }
}
