package it.polimi.ingsw.Model.ConclusionEvents;

import it.polimi.ingsw.Model.Game;

/**
 * When one color of Development Cards is no longer available, this event is notified.
 */
public class DevCardColorEnded extends ConclusionEvent {

    @Override
    public void handleEvent(Game game){
        game.endGame(this);
    }

    @Override
    public String eventTrigger()
    {
        return "one development card color is ended.";
    }
}
