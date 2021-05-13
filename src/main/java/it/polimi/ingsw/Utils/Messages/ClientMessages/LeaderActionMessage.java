package it.polimi.ingsw.Utils.Messages.ClientMessages;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.TurnController;
import it.polimi.ingsw.Network.RemoteView;

import java.util.Objects;

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

    public void handleMessage(TurnController turnController, RemoteView sender) {
        turnController.handleLeaderActionMessage(this,sender);
    }

    public void handleMessage(GameController gameController, RemoteView sender) {
        handleMessage(gameController.getTurnController(), sender);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeaderActionMessage that = (LeaderActionMessage) o;
        return index == that.index && toDiscard == that.toDiscard;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, toDiscard);
    }
}

