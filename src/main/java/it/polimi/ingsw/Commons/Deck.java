package it.polimi.ingsw.Commons;

import java.io.Serializable;
import java.util.Stack;

/**
 * This class represents a deck of development cards.
 * It implements the {@link Serializable} interface in order to be sent through the network.
 */

public class Deck implements Serializable {

    /**
     * The {@link CardType} of the development cards contained in this deck.
     */
    private final CardType cardType;

    /**
     * The {@link Stack} containing all the development cards stacked.
     */
    private final Stack<DevelopmentCard> cards;

    public Deck(CardType cardType, Stack<DevelopmentCard> cards) {
        this.cardType = cardType;
        this.cards = cards;
    }

    /**
     * Draws a development card from the deck.
     * @return the card drawn.
     */
    public DevelopmentCard draw(){
        return this.cards.pop();
    }

    /**
     * Gets the {@link DevelopmentCard} on top of the deck.
     * @return the card got.
     */
    public DevelopmentCard getTop(){
        return this.cards.peek();
    }

    /**
     * @return the number of cards stacked on the deck.
     */
    public int getSize()
    {
        return this.cards.size();
    }

    /**
     * @return whether the deck is empty.
     */
    public boolean isEmpty() { return cards.size()==0; }

    public CardType getCardType() {
        return cardType;
    }

    @Override
    public String toString() {
        return "Deck{" +
                "cardType=" + cardType +
                ", cards=" + cards +
                '}';
    }

    public Stack<DevelopmentCard> getCards() {
        return cards;
    }
}
