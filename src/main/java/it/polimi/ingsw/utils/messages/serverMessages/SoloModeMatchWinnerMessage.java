package it.polimi.ingsw.utils.messages.serverMessages;

import it.polimi.ingsw.model.gameEvents.ConclusionEvent;
import it.polimi.ingsw.network.Client;

/**
 * Class consisting of a message sent at the end of a solo mode game.
 */
public class SoloModeMatchWinnerMessage implements ServerMessage{
    /**
     * Boolean attribute that establishes if player won.
     */
    private final boolean playerWon;
    /**
     * If the player has won this number becomes relevant because
     * represents how many points has been gained.
     */
    private final int victoryPoints;
    /**
     * Event that triggered game conclusion.
     */
    private final ConclusionEvent conclusionEvent;

    public SoloModeMatchWinnerMessage(boolean playerWon, int victoryPoints,ConclusionEvent conclusionEvent) {
        this.playerWon = playerWon;
        this.victoryPoints = victoryPoints;
        this.conclusionEvent = conclusionEvent;
    }

    public boolean hasPlayerWon() {
        return playerWon;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public ConclusionEvent getConclusionEvent() {
        return conclusionEvent;
    }

    /**
     * Method called by client in order to show message itself.
     * @param handler {@link Client} instance that manages the update itself.
     */
    @Override
    public void handleMessage(Client handler) {
        handler.getUI().render(this);
    }
}
