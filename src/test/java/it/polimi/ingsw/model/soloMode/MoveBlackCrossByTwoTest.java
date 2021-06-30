package it.polimi.ingsw.model.soloMode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveBlackCrossByTwoTest {
    @Test
    void renderTokenImage() {
        MoveBlackCrossByTwo moveBlackCrossByTwo= new MoveBlackCrossByTwo();
        String tokenImage="gui/img/tokens/moveBy2.png";
        assertEquals(tokenImage,moveBlackCrossByTwo.renderTokenImage());
    }

    @Test
    void renderTokenEffect() {
        MoveBlackCrossByTwo moveBlackCrossByTwo= new MoveBlackCrossByTwo();
        String tokenEffect="Lorenzo moved his black cross forward by two spaces in the faith track";
        assertEquals(tokenEffect,moveBlackCrossByTwo.renderTokenEffect());
    }
}