package it.polimi.ingsw.Model.ConclusionEvents;

import it.polimi.ingsw.Model.Game;

public interface GameEvent {

    public void handleEvent(Game game);
}
