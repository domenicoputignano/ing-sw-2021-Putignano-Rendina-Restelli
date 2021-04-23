package it.polimi.ingsw.Model.ConclusionEvents;

import it.polimi.ingsw.Model.ConclusionEvents.ConclusionEvent;
import it.polimi.ingsw.Model.Game;

public class DevCardColorEnded extends ConclusionEvent {

    public DevCardColorEnded(Game game){
        super(game);
    }

    public void handleConclusion(){
        game.endGame(this);
    }
}
