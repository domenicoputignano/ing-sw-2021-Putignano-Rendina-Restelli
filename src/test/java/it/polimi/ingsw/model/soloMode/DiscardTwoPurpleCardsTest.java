package it.polimi.ingsw.model.soloMode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Test class that tests the activation event of the Discard Two Purple Cards in SoloMode
 */
class DiscardTwoPurpleCardsTest {

    @Test
    void renderTokenImage() {
        DiscardTwoPurpleCards discardTwoPurpleCards= new DiscardTwoPurpleCards();
        String tokenImage="gui/img/tokens/discard2purple.png";
        assertEquals(tokenImage,discardTwoPurpleCards.renderTokenImage());
    }

    @Test
    void renderTokenEffect() {
        DiscardTwoPurpleCards discardTwoPurpleCards= new DiscardTwoPurpleCards();
        String tokenEffect="Lorenzo discarded two purple cards from the decks";
        assertEquals(tokenEffect,discardTwoPurpleCards.renderTokenEffect());
    }
}