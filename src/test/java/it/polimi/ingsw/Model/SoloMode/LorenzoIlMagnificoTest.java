package it.polimi.ingsw.Model.SoloMode;

import it.polimi.ingsw.Model.FaithTrack;
import it.polimi.ingsw.Model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LorenzoIlMagnificoTest {
    Player player;
    SoloMode game;

    @Test
    void moveBlackCross() {
        Player player = new Player("Pippo");
        SoloMode game = new SoloMode(player);
        game.setup();
        LorenzoIlMagnifico lorenzo = new LorenzoIlMagnifico(game.getCurrPlayer().getPersonalBoard().getFaithTrack(),game);
        FaithTrack faithTrack = game.getCurrPlayer().getPersonalBoard().getFaithTrack();
        int start = faithTrack.getFaithMarker();
        faithTrack.moveMarker(23);
        assertEquals(start + 23,faithTrack.getFaithMarker());
        assertEquals(2, faithTrack.getPassedSection());
    }

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