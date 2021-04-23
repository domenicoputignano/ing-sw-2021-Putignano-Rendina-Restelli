package it.polimi.ingsw.Model.ConclusionEvents;

import it.polimi.ingsw.Model.ConclusionEvents.ConclusionEvent;
import it.polimi.ingsw.Model.Game;

public class BlackCrossHitLastSpace extends ConclusionEvent {

    public BlackCrossHitLastSpace(Game game) {
        super(game);
    }

    public void handleConclusion(){
        game.endGame(this);
    }
}
