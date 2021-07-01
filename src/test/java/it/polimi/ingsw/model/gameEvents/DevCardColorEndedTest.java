package it.polimi.ingsw.model.gameEvents;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.soloMode.SoloMode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
/**
 * Test class that tests the activation event of the Development Card Color Ended
 */
class DevCardColorEndedTest {
    /**
     * test method for handling the event
     */
    @Test
    void handleEvent() {
        Player player = new Player("pippo");
        SoloMode game = new SoloMode(player);
        SoloMode spyGame = spy(game);
        DevCardColorEnded devCardColorEnded = new DevCardColorEnded();
        devCardColorEnded.handleEvent(spyGame);
        verify(spyGame,times(1)).endGame(devCardColorEnded);
    }
    /**
     * test method for event Trigger
     */
    @Test
    void eventTrigger() {
        DevCardColorEnded devCardColorEnded = new DevCardColorEnded();
        String event="one development card color is ended.";
        String actual=devCardColorEnded.eventTrigger();
        assertEquals(event,actual);
    }
}