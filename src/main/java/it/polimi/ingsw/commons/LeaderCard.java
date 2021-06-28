package it.polimi.ingsw.commons;

import it.polimi.ingsw.utils.ANSI_Color;
import it.polimi.ingsw.utils.ResourceLocator;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class LeaderCard implements Serializable {
    /**
     * The unique ID identifying the card.
     */
    private final String ID;
    /**
     * The {@link LeaderEffect} associated to this card.
     */
    private final LeaderEffect leaderEffect;
    /**
     * The {@link ResourceType} types and occurrences needed to activate this card.
     */
    private final Map<ResourceType, Integer> requirementsResources;
    /**
     * The {@link DevelopmentCard} types and occurrences needed to activate this card.
     */
    private final List<CardType> requirementsCards;
    /**
     * The activation status of the card.
     */
    private boolean isActive;
    /**
     * The number of victory points earned by the player who buys it.
     */
    private final int victoryPoints;

    /**
     * This custom constructor is used only for test purposes.
     * Original leader cards are instead loaded parsing them from an external json source.
     */
    public LeaderCard(LeaderEffect leaderEffect, Map<ResourceType, Integer> requirementsResources, List<CardType> requirementsCards, int victoryPoints) {
        this.ID = "SA-SE";
        this.leaderEffect = leaderEffect;
        this.requirementsResources = requirementsResources;
        this.requirementsCards = requirementsCards;
        this.isActive = false;
        this.victoryPoints = victoryPoints;
    }

    public LeaderEffect getLeaderEffect() {
        return new LeaderEffect(this.leaderEffect.getEffect(), this.leaderEffect.getType());
    }

    public Map<ResourceType, Integer> getRequirementsResources() {
        return this.requirementsResources;
    }

    public List<CardType> getRequirementsCards() {
        return requirementsCards;
    }

    public boolean isActive() {
        return isActive;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void setIsActive(){
        this.isActive = true;
    }

    /**
     * @return a string representation of the card using ASCII escape codes to render colors.
     */

    @Override
    public String toString() {
        String color;
        switch (leaderEffect.getType()) {
            case shield:
                color = ANSI_Color.BLUE.escape();
                break;
            case stone:
                color = ANSI_Color.GREY.escape();
                break;
            case servant:
                color = ANSI_Color.PURPLE.escape();
                break;
            case coin:
                color = ANSI_Color.YELLOW.escape();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + leaderEffect.getType());
        }
        if(requirementsCards.size()>0) return color+"{" +
                "\nEffect =" + leaderEffect +
                "\nRequired Cards = " + requirementsCards +
                "\n"+activation()+
                "\nPoints = " + victoryPoints +
                "}"+ ANSI_Color.RESET;
        else return color+"{" +
                "\nEffect=" + leaderEffect +
                "\nRequired resources = " + requirementsResources +
                "\n"+activation()+
                "\nPoints =" + victoryPoints +
                '}'+ ANSI_Color.RESET;
    }

    /**
     * @return a string representing the activation status of this card.
     */
    private String activation() {
        if(isActive) return "active";
        else return "inactive";
    }

    /**
     * Prints the card as a string using ASCII escape codes to render colors.
     */
    public void displayASCII() {
        ResourceLocator.printLeaderCardASCII(this.ID);
    }

    /**
     * Returns the unique image associated to this card by calling the {@link ResourceLocator} class
     * in order to retrieve the image.
     * @return the string of the path to the image.
     */
    public String toImage(){
        return ResourceLocator.retrieveLeaderCardImage(this.ID);
    }

}
