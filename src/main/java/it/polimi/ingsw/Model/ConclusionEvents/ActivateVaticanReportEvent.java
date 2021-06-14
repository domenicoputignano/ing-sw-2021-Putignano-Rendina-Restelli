package it.polimi.ingsw.Model.ConclusionEvents;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player;

public class ActivateVaticanReportEvent implements GameEvent{
    private final Integer vatican_index;
    private final Player triggeringPlayer;

    public ActivateVaticanReportEvent(Player trigger, Integer vatican_index) {
        this.vatican_index = vatican_index;
        this.triggeringPlayer = trigger;
    }

    @Override
    public void handleEvent(Game game) {
        game.activateVaticanReport(triggeringPlayer, vatican_index);
    }

    @Override
    public String eventTrigger()
    {
        return "Vatican Report on Section "+vatican_index+" has been activated.";
    }
}
