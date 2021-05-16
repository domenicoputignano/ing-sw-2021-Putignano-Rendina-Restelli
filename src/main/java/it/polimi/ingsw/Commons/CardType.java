package it.polimi.ingsw.Commons;


import java.io.Serializable;

public class CardType implements Serializable {
    private final int level;
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
