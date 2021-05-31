package it.polimi.ingsw.Commons;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.Stack;

public class Slot implements Cloneable, Serializable {

    private final Stack<DevelopmentCard> developmentCardStack;

    public Slot() {
        developmentCardStack = new Stack<DevelopmentCard>();
    }

    private Slot(Slot initialSlot) {
        developmentCardStack = new Stack<>();
        developmentCardStack.addAll(initialSlot.developmentCardStack);
    }

    public DevelopmentCard peekTopCard() {
        return developmentCardStack.peek();
    }

    public void putCardOnTop(DevelopmentCard developmentCard) {
        developmentCardStack.push(developmentCard);
    }

    public int getNumOfStackedCards() {
        return developmentCardStack.size();
    }

    public Stack<DevelopmentCard> getDevelopmentCardStack() {
        return developmentCardStack;
    }

    public Slot clone() {
        return new Slot(this);
    }

    public int getVictoryPointsSlot()
    {
        return developmentCardStack.stream().map(DevelopmentCard::getVictoryPoints).reduce(0,Integer::sum);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Slot slot = (Slot) o;
        return Objects.equals(developmentCardStack, slot.developmentCardStack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(developmentCardStack);
    }


}
