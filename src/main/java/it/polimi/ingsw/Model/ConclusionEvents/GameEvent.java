package it.polimi.ingsw.Model.ConclusionEvents;

import it.polimi.ingsw.Model.Game;

import java.io.Serializable;

public interface GameEvent extends Serializable {

    public void handleEvent(Game game);

    public String eventTrigger();
}
