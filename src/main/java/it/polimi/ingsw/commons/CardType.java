package it.polimi.ingsw.commons;


import java.io.Serializable;

/**
 *  This class represents a type of development card, which is a couple composed of a level and a color.
 *  It implements the Serializable interface in order to be sent through the network.
 */

public class CardType implements Serializable {
    /**
     * The level of the card
     */
    private final int level;

    /**
     * The color of the card
     */
    private final ColorCard color;

    public CardType(int level, ColorCard color) {
        this.level = level;
        this.color = color;
    }

    public int getLevel() {
        return level;
    }

    public ColorCard getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardType cardType = (CardType) o;
        return level == cardType.level && color == cardType.color;
    }

    @Override
    public String toString() {
        return "{" +
                "level=" + level +
                ", color=" + color +
                '}';
    }
}
