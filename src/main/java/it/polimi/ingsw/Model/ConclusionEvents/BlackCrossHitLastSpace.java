package it.polimi.ingsw.Model.ConclusionEvents;

import it.polimi.ingsw.Model.ConclusionEvents.ConclusionEvent;
import it.polimi.ingsw.Model.Game;

public class BlackCrossHitLastSpace extends ConclusionEvent {

    @Override
    public void handleEvent(Game game){
        game.endGame(this);
    }
}
