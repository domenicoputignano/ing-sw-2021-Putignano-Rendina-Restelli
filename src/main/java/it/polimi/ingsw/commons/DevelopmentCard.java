package it.polimi.ingsw.commons;

import it.polimi.ingsw.model.ProductionRule;
import it.polimi.ingsw.utils.ResourceLocator;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

/**
 * Represents a Development Card of Maestri del Rinascimento board game.
 * It is composed by a {@link CardType}, the number of victory points earned by buying it,
 * the cost in terms of {@link ResourceType}, the {@link ProductionRule} which it permits.
 * It implements {@link Serializable} interface in order to be sent through the network.
 */

public class DevelopmentCard implements Serializable {
    /**
     * The unique ID identifying the card.
     */
    private final String ID;
    /**
     * The cost in terms of number of {@link ResourceType} needed to buy the card.
     */
    private final Map<ResourceType, Integer> cost;
    /**
     * The {@link CardType} associated.
     */
    private final CardType type;
    /**
     * The number of victory points earned by the player who buys it.
     */
    private final int victoryPoints;
    /**
     * The {@link ProductionRule} which it permits.
     */
    private final ProductionRule trade;

    /**
     * This custom constructor is used only for test purposes.
     * Original cards are instead loaded parsing them from an external json source.
     */
    public DevelopmentCard(Map<ResourceType, Integer> cost, int level, ColorCard color, int victoryPoints, ProductionRule trade){
        this.ID = "1B";
        this.cost = new EnumMap<>(cost);
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

    /**
     * Returns the string representing the card using ASCII escape codes to render colors.
     */
    @Override
    public String toString() {
        return
                ".---------------------------.\n" +
                        "|"+toASCII(1)+"|\n"+
                        "|"+toASCII(2)+"|\n"+
                        "|      .-------------.      |\n"+
                        "|"+toASCII(4)+"|\n"+
                        "|"+toASCII(5)+"|\n"+
                        "|"+toASCII(6)+"|\n"+
                        "|      '.............'      |\n"+
                        "|"+toASCII(8)+"|\n"+
                        "'---------------------------'\n";
    }

    /**
     * @param row of the card to print.
     * @return the ASCII string of the specified row.
     */
    public String toASCII(int row) {
        return ResourceLocator.retrieveDevCardASCII(this.ID, row);
    }

    /**
     * Returns the unique image associated to this card by calling the {@link ResourceLocator} class
     * in order to retrieve the image.
     * @return the string of the path to the image.
     */
    public String toImage(){
        return ResourceLocator.retrieveDevCardImage(this.ID);
    }
}
