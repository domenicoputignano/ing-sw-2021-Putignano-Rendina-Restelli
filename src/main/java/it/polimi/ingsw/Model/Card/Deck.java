package it.polimi.ingsw.Model.Card;

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

    // TODO DA CAMBIARE, ESPONE L'IMPLEMENTAZIONE PRIVATA DELL'ATTRIBUTO
    public Stack<DevelopmentCard> getCards() {
        return cards;
        //TODO : DA MODIFICARE
    }
}
