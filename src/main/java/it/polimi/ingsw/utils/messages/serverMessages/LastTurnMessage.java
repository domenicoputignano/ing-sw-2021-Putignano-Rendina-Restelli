package it.polimi.ingsw.utils.messages.serverMessages;

import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.model.gameEvents.ConclusionEvent;
import it.polimi.ingsw.network.Client;

/**
 * Class representing a message sent from server when a player makes a move that starts
 * ending phase of the game.
 */
public class LastTurnMessage implements ServerMessage{

    /**
     * User of the player who caused game end.
     */
    private User triggeringUser;
    /**
     * Event that caused game end.
     */
    private ConclusionEvent conclusionEvent;

    public LastTurnMessage(User triggeringUser, ConclusionEvent conclusionEvent) {
        this.triggeringUser = triggeringUser;
        this.conclusionEvent = conclusionEvent;
    }
    /**
     * Method called by client in order to show message itself.
     * @param handler {@link Client} instance that manages the update itself.
     */
    @Override
    public void handleMessage(Client handler) {
        handler.getUI().render(this);
    }

    public User getTriggeringUser() {
        return triggeringUser;
    }

    public ConclusionEvent getConclusionEvent() {
        return conclusionEvent;
    }
}
