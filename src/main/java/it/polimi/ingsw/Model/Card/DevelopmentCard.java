package it.polimi.ingsw.Model.Card;

import it.polimi.ingsw.Model.ProductionRule;
import it.polimi.ingsw.Model.ResourceType;

import java.util.EnumMap;
import java.util.Map;

public class DevelopmentCard {
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
}
