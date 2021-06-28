package it.polimi.ingsw.Model.ConclusionEvents;

import it.polimi.ingsw.Model.Game;

import java.io.Serializable;

/**
 * This interface is implements by al the possible events which can occur during the game.
 * Parts of the model notifies the central class {@link Game} when an event occurs and it
 * handles it by calling the method on these classes.
 * It implements {@link Serializable} interface in order to be sent through the network.
 */
public interface GameEvent extends Serializable {

    /**
     * The method which is called by {@link Game} class in order to handle the event.
     * Each event overrides it.
     * @param game the game instance.
     */
    void handleEvent(Game game);

    /**
     * @return the event trigger as a string.
     */
    String eventTrigger();
}
