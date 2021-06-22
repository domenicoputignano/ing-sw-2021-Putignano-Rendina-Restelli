package it.polimi.ingsw.Model.ConclusionEvents;

import it.polimi.ingsw.Model.ConclusionEvents.ConclusionEvent;
import it.polimi.ingsw.Model.Game;

public class HitLastSpace extends ConclusionEvent {

     @Override
     public void handleEvent(Game game){
         game.endGame(this);
     }

     @Override
     public String eventTrigger()
     {
         return "sast space has been hit.";
     }
}
