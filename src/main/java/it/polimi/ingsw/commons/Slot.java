package it.polimi.ingsw.commons;

import java.io.Serializable;
import java.util.Objects;
import java.util.Stack;

/**
 * This class represents a slot of the {@link it.polimi.ingsw.model.PersonalBoard}.
 * It permits to stack Development Cards on it.
 * It implements {@link Serializable} interface in order to be sent throught he network.
 */
public class Slot implements Cloneable, Serializable {

    /**
     * The data structure where Development Cards are stacked.
     */
    private final Stack<DevelopmentCard> developmentCardStack;

    public Slot() {
        developmentCardStack = new Stack<>();
    }

    private Slot(Slot initialSlot) {
        developmentCardStack = new Stack<>();
        developmentCardStack.addAll(initialSlot.developmentCardStack);
    }

    /**
     * @return the {@link DevelopmentCard} on top of the slot.
     */
    public DevelopmentCard peekTopCard() {
        return developmentCardStack.peek();
    }

    /**
     * Puts a {@link DevelopmentCard} on top of the slot.
     * @param developmentCard the card to put on top.
     */
    public void putCardOnTop(DevelopmentCard developmentCard) {
        developmentCardStack.push(developmentCard);
    }

    /**
     * @return the num of cards stacked on the slot.
     */
    public int getNumOfStackedCards() {
        return developmentCardStack.size();
    }

    public Stack<DevelopmentCard> getDevelopmentCardStack() {
        return developmentCardStack;
    }

    public Slot clone() {
        return new Slot(this);
    }

    /**
     * @return the overall number of victory points of the Development Cards stacked on this slot.
     */
    public int getVictoryPointsSlot() {
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
