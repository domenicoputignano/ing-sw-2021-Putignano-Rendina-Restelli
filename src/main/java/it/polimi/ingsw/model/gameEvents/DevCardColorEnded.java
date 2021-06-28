package it.polimi.ingsw.model.gameEvents;

import it.polimi.ingsw.model.Game;

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
