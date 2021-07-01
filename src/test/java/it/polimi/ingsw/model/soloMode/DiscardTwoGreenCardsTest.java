package it.polimi.ingsw.model.soloMode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Test class that tests the activation event of the Discard Two Green Cards in SoloMode
 */
class DiscardTwoGreenCardsTest {

    @Test
    void renderTokenImage() {
        DiscardTwoGreenCards discardTwoGreenCards= new DiscardTwoGreenCards();
        String tokenImage="gui/img/tokens/discard2green.png";
        assertEquals(tokenImage,discardTwoGreenCards.renderTokenImage());
    }

    @Test
    void renderTokenEffect() {
        DiscardTwoGreenCards discardTwoGreenCards= new DiscardTwoGreenCards();
        String tokenEffect="Lorenzo discarded two green cards from the decks";
        assertEquals(tokenEffect,discardTwoGreenCards.renderTokenEffect());
    }
}