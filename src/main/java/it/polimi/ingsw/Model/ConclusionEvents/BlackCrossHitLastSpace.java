package it.polimi.ingsw.Model.ConclusionEvents;

import it.polimi.ingsw.Model.Game;

/**
 * When Lorenzo il Magnifico reaches last space on the Faith Track, this event is notified.
 */
public class BlackCrossHitLastSpace extends ConclusionEvent {

    @Override
    public void handleEvent(Game game){
        game.endGame(this);
    }

    @Override
    public String eventTrigger()
    {
        return "Lorenzo il Magnifico hit last space.";
    }
}
