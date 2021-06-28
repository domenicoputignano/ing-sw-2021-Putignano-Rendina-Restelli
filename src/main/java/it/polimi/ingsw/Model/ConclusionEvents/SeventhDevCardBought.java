package it.polimi.ingsw.Model.ConclusionEvents;

import it.polimi.ingsw.Model.Game;

/**
 * When a player buys his seventh {@link it.polimi.ingsw.Commons.DevelopmentCard}, this event is notified.
 */
public class SeventhDevCardBought extends ConclusionEvent {

    @Override
    public void handleEvent(Game game){
        game.endGame(this);
    }

    @Override
    public String eventTrigger()
    {
        return "seventh Card has been bought.";
    }
}
