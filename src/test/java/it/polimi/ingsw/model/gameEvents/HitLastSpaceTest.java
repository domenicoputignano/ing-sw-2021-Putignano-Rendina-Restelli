package it.polimi.ingsw.model.gameEvents;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.soloMode.SoloMode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
/**
 * Test class that tests the activation event of the faith Hit Last Space
 */
class HitLastSpaceTest {
    /**
     * test method for handling the event
     */
    @Test
    void handleEvent() {
        Player player = new Player("pippo");
        SoloMode game = new SoloMode(player);
        SoloMode spyGame = spy(game);
        HitLastSpace hitLastSpace = new HitLastSpace();
        hitLastSpace.handleEvent(spyGame);
        verify(spyGame,times(1)).endGame(hitLastSpace);
    }
    /**
     * test method for event Trigger
     */
    @Test
    void eventTrigger() {
        HitLastSpace hitLastSpace = new HitLastSpace();
        String event="last space has been hit.";
        String actual=hitLastSpace.eventTrigger();
        assertEquals(event,actual);
    }
}