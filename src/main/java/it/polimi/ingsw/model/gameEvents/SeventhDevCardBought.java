package it.polimi.ingsw.model.gameEvents;

import it.polimi.ingsw.model.Game;

/**
 * When a player buys his seventh {@link it.polimi.ingsw.commons.DevelopmentCard}, this event is notified.
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
