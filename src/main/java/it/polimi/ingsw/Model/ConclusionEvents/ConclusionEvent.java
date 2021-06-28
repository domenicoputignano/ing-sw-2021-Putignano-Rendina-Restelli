package it.polimi.ingsw.Model.ConclusionEvents;

import it.polimi.ingsw.Model.Game;

/**
 * This abstract class is extended by all the possible Conclusion Events which can
 * occur during the game.
 */
public abstract class ConclusionEvent implements GameEvent {

    @Override
    public void handleEvent(Game game){}
}
