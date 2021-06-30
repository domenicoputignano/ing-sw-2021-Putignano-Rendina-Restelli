package it.polimi.ingsw.model.soloMode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveBlackCrossAndShuffleTest {

    @Test
    void renderTokenImage() {
        MoveBlackCrossAndShuffle moveBlackCrossAndShuffle= new MoveBlackCrossAndShuffle();
        String tokenImage="gui/img/tokens/moveBy1andShuffle.png";
        assertEquals(tokenImage,moveBlackCrossAndShuffle.renderTokenImage());
    }

    @Test
    void renderTokenEffect() {
        MoveBlackCrossAndShuffle moveBlackCrossAndShuffle= new MoveBlackCrossAndShuffle();
        String tokenEffect="Lorenzo moved his black cross forward by one space on the faith track and shuffled tokens";
        assertEquals(tokenEffect,moveBlackCrossAndShuffle.renderTokenEffect());
    }
}