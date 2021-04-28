package it.polimi.ingsw.Utils.Messages;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.TurnController;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Utils.Messages.ClientMessage;

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
        if(index<=0 || index>2) return false;
        return true;
    }

    public void handleMessage(TurnController turnController, Player sender) {
        turnController.handleLeaderActionMessage(this);
    }

    public void handleMessage(GameController gameController, Player sender) {
        handleMessage(gameController.getTurnController(), sender);
    }
}
