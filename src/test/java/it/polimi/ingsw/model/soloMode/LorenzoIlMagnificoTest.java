package it.polimi.ingsw.model.soloMode;

import it.polimi.ingsw.model.FaithTrack;
import it.polimi.ingsw.model.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Test class that tests the actions performed by Lorenzo il Magnifico
 */
class LorenzoIlMagnificoTest {
    Player player;
    SoloMode game;

    /**
     * test method that tests the correct execution of the movement of the black cross
     */
    @Test
    void moveBlackCross() {
        Player player = new Player("Pippo");
        SoloMode game = new SoloMode(player);
        game.setup();
        LorenzoIlMagnifico lorenzo = new LorenzoIlMagnifico(game.getCurrPlayer().getPersonalBoard().getFaithTrack(),game);
        FaithTrack faithTrack = game.getCurrPlayer().getPersonalBoard().getFaithTrack();
        int start = faithTrack.getFaithMarker();
        faithTrack.moveMarker(game.getCurrPlayer(), 23);
        assertEquals(start + 23,faithTrack.getFaithMarker());
        assertEquals(2, faithTrack.getPassedSection());
    }

    /**
     * test method that tests the correct execution of the movement
     * of the black cross and the shuffling of the tokens
     */
    @Test
    void moveAndShuffle() {
        Player player = new Player("Pippo");
        SoloMode game = new SoloMode(player);
        game.setup();
        LorenzoIlMagnifico lorenzo = new LorenzoIlMagnifico(game.getCurrPlayer().getPersonalBoard().getFaithTrack(),game);
        int startBlackCross = lorenzo.getBlackCross();
        lorenzo.moveAndShuffle();
        assertEquals(lorenzo.getBlackCross(),startBlackCross+1);
        assertTrue(game.getTokens().size()==7);
    }
}