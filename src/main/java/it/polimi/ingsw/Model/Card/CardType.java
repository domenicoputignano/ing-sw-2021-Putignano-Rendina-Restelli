package it.polimi.ingsw.Model.Card;



public class CardType {
    private int level;
    private ColorCard color;

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
}
