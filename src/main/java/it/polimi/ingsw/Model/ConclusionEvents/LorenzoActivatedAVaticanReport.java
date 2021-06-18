package it.polimi.ingsw.Model.ConclusionEvents;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.SoloMode.SoloMode;

public class LorenzoActivatedAVaticanReport implements GameEvent {

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
