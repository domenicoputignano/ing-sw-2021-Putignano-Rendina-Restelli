package it.polimi.ingsw.Utils.Messages.ServerMessages;

import it.polimi.ingsw.Model.ConclusionEvents.ConclusionEvent;
import it.polimi.ingsw.Network.Client;

public class SoloModeMatchWinnerMessage implements ServerMessage{

    private final boolean playerWon;
    private final int victoryPoints;
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

    @Override
    public void handleMessage(Client handler) {
        handler.getUI().render(this);
    }
}
