package it.polimi.ingsw.model.gameEvents;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.soloMode.SoloMode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SeventhDevCardBoughtTest {

    @Test
    void handleEvent() {
        Player player = new Player("pippo");
        SoloMode game = new SoloMode(player);
        SoloMode spyGame = spy(game);
        SeventhDevCardBought seventhDevCardBought = new SeventhDevCardBought();
        seventhDevCardBought.handleEvent(spyGame);
        verify(spyGame,times(1)).endGame(seventhDevCardBought);
    }

    @Test
    void eventTrigger() {
        SeventhDevCardBought seventhDevCardBought = new SeventhDevCardBought();
        String event="seventh Card has been bought.";
        String actual=seventhDevCardBought.eventTrigger();
        assertEquals(event,actual);
    }
}