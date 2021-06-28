package it.polimi.ingsw.Model.ConclusionEvents;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.SoloMode.SoloMode;

/**
 * When Lorenzo il Magnifico activates a vatican report, this event is notified to the {@link Game} class.
 */
public class LorenzoActivatedAVaticanReport implements GameEvent {
    /**
     * The index of the {@link it.polimi.ingsw.Model.VaticanReportSection} where to Vatican report has
     * been activated.
     */
    int vaticanIndex;

    public LorenzoActivatedAVaticanReport(int vaticanIndex) {
        this.vaticanIndex = vaticanIndex;
    }

    @Override
    public void handleEvent(Game game) {
        ((SoloMode)game).LorenzoActivatedAVaticanReport(vaticanIndex);
    }

    @Override
    public String eventTrigger() {
        return "Lorenzo activated a Vatican Report";
    }
}
