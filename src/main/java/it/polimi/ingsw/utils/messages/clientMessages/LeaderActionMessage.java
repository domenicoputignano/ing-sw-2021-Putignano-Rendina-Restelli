package it.polimi.ingsw.utils.messages.clientMessages;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.network.RemoteView;

import java.util.Objects;

/**
 * Class that contains information useful to perform a {@link it.polimi.ingsw.model.LeaderAction}.
 */
public class LeaderActionMessage  implements TurnControllerHandleable {
    /**
     * Index of leader card on which the action has to be performed.
     */
    private int index;
    /**
     * Variable that establishes if leader card has to be discarded.
     */
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

    /**
     * Boolean method that checks message correctness through index inspection.
     */
    @Override
    public boolean isValidMessage()
    {
        return index > 0 && index <= 2;
    }

    /**
     * Calls right method of turn controller in order to process the message itself and perform the action required by player.
     * @param turnController turn controller instance that process the message.
     * @param sender remote view that forwards the message.
     */
    public void handleMessage(TurnController turnController, RemoteView sender) {
        turnController.handleLeaderActionMessage(this,sender);
    }

    /**
     * This method is called in order to forward message itself to turn controller.
     * @param gameController game controller instance that will process the message.
     * @param sender remote view that forwards the message.
     */
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

