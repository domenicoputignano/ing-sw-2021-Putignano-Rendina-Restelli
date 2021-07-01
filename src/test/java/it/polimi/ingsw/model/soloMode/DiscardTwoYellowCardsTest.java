package it.polimi.ingsw.model.soloMode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Test class that tests the activation event of the Discard Two Yellow Cards in SoloMode
 */
class DiscardTwoYellowCardsTest {

    @Test
    void renderTokenImage() {
        DiscardTwoYellowCards discardTwoYellowCards= new DiscardTwoYellowCards();
        String tokenImage="gui/img/tokens/discard2yellow.png";
        assertEquals(tokenImage,discardTwoYellowCards.renderTokenImage());
    }

    @Test
    void renderTokenEffect() {
        DiscardTwoYellowCards discardTwoYellowCards= new DiscardTwoYellowCards();
        String tokenEffect="Lorenzo discarded two yellow cards from the decks";
        assertEquals(tokenEffect,discardTwoYellowCards.renderTokenEffect());
    }
}