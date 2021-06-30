package it.polimi.ingsw.model.soloMode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiscardTwoBlueCardsTest {


    @Test
    void renderTokenImage() {
        DiscardTwoBlueCards discardTwoBlueCards= new DiscardTwoBlueCards();
        String tokenImage="gui/img/tokens/discard2blue.png";
        assertEquals(tokenImage,discardTwoBlueCards.renderTokenImage());
    }

    @Test
    void renderTokenEffect() {
        DiscardTwoBlueCards discardTwoBlueCards= new DiscardTwoBlueCards();
        String tokenEffect="Lorenzo discarded two blue cards from the decks";
        assertEquals(tokenEffect,discardTwoBlueCards.renderTokenEffect());
    }
}