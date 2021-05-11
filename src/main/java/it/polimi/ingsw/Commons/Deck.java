package it.polimi.ingsw.Commons;

import java.util.Stack;

public class Deck {

    private CardType cardType;
    private Stack<DevelopmentCard> cards;

    public Deck(CardType cardType, Stack<DevelopmentCard> cards) {
        this.cardType = cardType;
        this.cards = cards;
    }

    public CardType getCardType() {
        return cardType;
    }

    public DevelopmentCard draw(){
        return this.cards.pop();
    }

    public DevelopmentCard getTop(){
        return this.cards.peek();
    }

    public int getSize()
    {
        return this.cards.size();
    }

    public boolean isEmpty() { return cards.size()==0; }

    @Override
    public String toString() {
        return "Deck{" +
                "cardType=" + cardType +
                ", cards=" + cards +
                '}';
    }

    // TODO DA CAMBIARE, ESPONE L'IMPLEMENTAZIONE PRIVATA DELL'ATTRIBUTO
    public Stack<DevelopmentCard> getCards() {
        return cards;
        //TODO : DA MODIFICARE
    }
}
