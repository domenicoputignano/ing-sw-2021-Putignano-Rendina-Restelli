package it.polimi.ingsw.Model.ConclusionEvents;

import it.polimi.ingsw.Model.ConclusionEvents.ConclusionEvent;
import it.polimi.ingsw.Model.Game;

public class HitLastSpace extends ConclusionEvent {

     public HitLastSpace(Game game){
         super(game);
     }

     public void handleConclusion(){
         game.endGame(this);
     }
}
