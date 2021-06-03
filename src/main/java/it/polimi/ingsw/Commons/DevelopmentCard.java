package it.polimi.ingsw.Commons;

import it.polimi.ingsw.Model.ProductionRule;
import it.polimi.ingsw.Utils.ResourceLocator;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

public class DevelopmentCard implements Serializable {
    private final String ID;
    private final Map<ResourceType, Integer> cost;
    private final CardType type;
    private final int victoryPoints;
    private final ProductionRule trade;

    // Used only for test purposes
    public DevelopmentCard(Map<ResourceType, Integer> cost, int level, ColorCard color, int victoryPoints, ProductionRule trade){
        this.ID = "1B";
        this.cost = new EnumMap<ResourceType, Integer>(cost);
        this.type = new CardType(level, color);
        this.victoryPoints = victoryPoints;
        this.trade = trade;
    }

    public Map<ResourceType, Integer> getCost() {
        return cost;
    }

    public CardType getType() {
        return type;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public ProductionRule getTrade() {
        return trade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DevelopmentCard that = (DevelopmentCard) o;
        return victoryPoints == that.victoryPoints && cost.equals(that.cost) && type.equals(that.type) && trade.equals(that.trade);
    }

    @Override
    public String toString() {
        return "DevelopmentCard{" +
                "cost=" + cost +
                ", type=" + type +
                ", victoryPoints=" + victoryPoints +
                ", trade=" + trade +
                '}';
    }

    public String toASCII(int row) {
        return ResourceLocator.retrieveDevCardASCII(this.ID, row);
    }

    public String toImage(){
        return ResourceLocator.retrieveDevCardImage(this.ID);
    }
}
