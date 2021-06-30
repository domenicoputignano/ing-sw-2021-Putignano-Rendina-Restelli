package it.polimi.ingsw.model.gameEvents;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.soloMode.SoloMode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BlackCrossHitLastSpaceTest {

    @Test
    void handleEvent() {
        Player player = new Player("pippo");
        SoloMode game = new SoloMode(player);
        SoloMode spyGame = spy(game);
        BlackCrossHitLastSpace blackCrossHitLastSpace = new BlackCrossHitLastSpace();
        blackCrossHitLastSpace.handleEvent(spyGame);
        verify(spyGame,times(1)).endGame(blackCrossHitLastSpace);
    }

    @Test
    void eventTrigger() {
        BlackCrossHitLastSpace blackCrossHitLastSpace = new BlackCrossHitLastSpace();
        String event="Lorenzo il Magnifico hit last space.";
        assertEquals(event,blackCrossHitLastSpace.eventTrigger());
    }
}