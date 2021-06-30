package it.polimi.ingsw.model.gameEvents;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.soloMode.SoloMode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LorenzoActivatedAVaticanReportTest {

    @Test
    void handleEvent() {
        Player player = new Player("pippo");
        SoloMode game = new SoloMode(player);
        SoloMode spyGame = spy(game);
        LorenzoActivatedAVaticanReport lorenzoActivatedAVaticanReport = new LorenzoActivatedAVaticanReport(1);
        lorenzoActivatedAVaticanReport.handleEvent(spyGame);
        verify(spyGame,times(1)).LorenzoActivatedAVaticanReport(1);
    }

    @Test
    void eventTrigger() {
        LorenzoActivatedAVaticanReport lorenzoActivatedAVaticanReport = new LorenzoActivatedAVaticanReport(1);
        String event="Lorenzo activated a Vatican Report";
        String actual=lorenzoActivatedAVaticanReport.eventTrigger();
        assertEquals(event,actual);
    }
}