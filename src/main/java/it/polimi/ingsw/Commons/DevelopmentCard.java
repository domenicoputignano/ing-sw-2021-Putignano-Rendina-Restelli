package it.polimi.ingsw.Commons;

import it.polimi.ingsw.Model.ProductionRule;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

public class DevelopmentCard implements Serializable {
    private Map<ResourceType, Integer> cost;
    private CardType type;
    private int victoryPoints;
    private ProductionRule trade;

    public DevelopmentCard(Map<ResourceType, Integer> cost, int level, ColorCard color, int victoryPoints, ProductionRule trade){
        this.cost = new EnumMap<ResourceType, Integer>(cost);
        this.type = new CardType(level, color);
        this.victoryPoints = victoryPoints;
        this.trade = trade;
    }

    // TODO DA CAMBIARE ESPONE L'IMPLEMENTAZIONE
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
}
