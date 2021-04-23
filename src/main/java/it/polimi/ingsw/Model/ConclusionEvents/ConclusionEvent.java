package it.polimi.ingsw.Model.ConclusionEvents;

import it.polimi.ingsw.Model.Game;

public abstract class ConclusionEvent {
    Game game;

    protected ConclusionEvent(Game game){
        this.game = game;
    }
}
