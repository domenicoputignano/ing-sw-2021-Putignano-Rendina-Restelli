package it.polimi.ingsw.model.gameEvents;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

/**
 * When a player activates a vatican report, this event is notified to the {@link Game} class.
 */
public class ActivateVaticanReportEvent implements GameEvent{
    /**
     * The index of the {@link it.polimi.ingsw.model.VaticanReportSection} where to Vatican report has
     * been activated.
     */
    private final Integer vatican_index;
    /**
     * The player who activated the Vatican Report.
     */
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
    public String eventTrigger() {
        return "Vatican Report on Section "+vatican_index+" has been activated.";
    }
}
