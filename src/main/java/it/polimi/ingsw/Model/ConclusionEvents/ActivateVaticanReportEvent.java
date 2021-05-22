package it.polimi.ingsw.Model.ConclusionEvents;

import it.polimi.ingsw.Model.Game;

public class ActivateVaticanReportEvent implements GameEvent{
    private final Integer vatican_index;

    public ActivateVaticanReportEvent(Integer vatican_index) {
        this.vatican_index = vatican_index;
    }

    @Override
    public void handleEvent(Game game) {
        game.activateVaticanReport(vatican_index);
    }
}
