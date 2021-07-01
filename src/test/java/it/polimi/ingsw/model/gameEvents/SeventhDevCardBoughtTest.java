package it.polimi.ingsw.model.gameEvents;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.soloMode.SoloMode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
/**
 * Test class that tests the activation event of the Seventh Development Card Bought
 */
class SeventhDevCardBoughtTest {
    /**
     * test method for handling the event
     */
    @Test
    void handleEvent() {
        Player player = new Player("pippo");
        SoloMode game = new SoloMode(player);
        SoloMode spyGame = spy(game);
        SeventhDevCardBought seventhDevCardBought = new SeventhDevCardBought();
        seventhDevCardBought.handleEvent(spyGame);
        verify(spyGame,times(1)).endGame(seventhDevCardBought);
    }
    /**
     * test method for event Trigger
     */
    @Test
    void eventTrigger() {
        SeventhDevCardBought seventhDevCardBought = new SeventhDevCardBought();
        String event="seventh Card has been bought.";
        String actual=seventhDevCardBought.eventTrigger();
        assertEquals(event,actual);
    }
}