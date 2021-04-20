package it.polimi.ingsw.Model.Card;


import java.util.Objects;

public class CardType {
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

}
