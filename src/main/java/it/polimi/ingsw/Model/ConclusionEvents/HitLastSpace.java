package it.polimi.ingsw.Model.ConclusionEvents;

import it.polimi.ingsw.Model.Game;

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
