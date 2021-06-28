package it.polimi.ingsw.model.gameEvents;

import it.polimi.ingsw.model.Game;

/**
 * This abstract class is extended by all the possible Conclusion Events which can
 * occur during the game.
 */
public abstract class ConclusionEvent implements GameEvent {

    @Override
    public void handleEvent(Game game){}
}
