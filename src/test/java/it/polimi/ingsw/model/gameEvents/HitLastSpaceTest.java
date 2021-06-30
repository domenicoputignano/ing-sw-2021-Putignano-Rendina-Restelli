package it.polimi.ingsw.model.gameEvents;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.soloMode.SoloMode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HitLastSpaceTest {

    @Test
    void handleEvent() {
        Player player = new Player("pippo");
        SoloMode game = new SoloMode(player);
        SoloMode spyGame = spy(game);
        HitLastSpace hitLastSpace = new HitLastSpace();
        hitLastSpace.handleEvent(spyGame);
        verify(spyGame,times(1)).endGame(hitLastSpace);
    }

    @Test
    void eventTrigger() {
        HitLastSpace hitLastSpace = new HitLastSpace();
        String event="last space has been hit.";
        String actual=hitLastSpace.eventTrigger();
        assertEquals(event,actual);
    }
}