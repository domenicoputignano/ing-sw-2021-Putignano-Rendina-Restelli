package it.polimi.ingsw.model.gameEvents;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.soloMode.SoloMode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class that tests the activation event of the activate Vatican Report
 */
class ActivateVaticanReportEventTest {
    /**
     * test method for handling the event
     */
    @Test
    void handleEvent() {
        Player player = new Player("pippo");
        SoloMode game = new SoloMode(player);
        SoloMode spyGame = spy(game);
        ActivateVaticanReportEvent activateVaticanReportEvent = new ActivateVaticanReportEvent(player,1);
        activateVaticanReportEvent.handleEvent(spyGame);
        verify(spyGame,times(1)).activateVaticanReport(player,1);
    }
    /**
     * test method for event Trigger
     */
    @Test
    void eventTrigger() {
        Player player = new Player("pippo");
        ActivateVaticanReportEvent activateVaticanReportEvent = new ActivateVaticanReportEvent(player,1);
        String event="Vatican Report on Section 1 has been activated.";
        String actual=activateVaticanReportEvent.eventTrigger();
        assertEquals(event,actual);
    }
}