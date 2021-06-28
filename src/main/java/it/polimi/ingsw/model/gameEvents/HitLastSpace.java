package it.polimi.ingsw.model.gameEvents;

import it.polimi.ingsw.model.Game;

/**
 * When a player reaches the last space on his Faith Track, this event is notified.
 */
public class HitLastSpace extends ConclusionEvent {

     @Override
     public void handleEvent(Game game){
         game.endGame(this);
     }

     @Override
     public String eventTrigger()
     {
         return "last space has been hit.";
     }
}
