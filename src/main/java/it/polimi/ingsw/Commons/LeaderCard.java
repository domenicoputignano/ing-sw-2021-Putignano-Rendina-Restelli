package it.polimi.ingsw.Commons;

import it.polimi.ingsw.Utils.ANSI_Color;
import it.polimi.ingsw.Utils.ResourceLocator;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class LeaderCard implements Serializable {
    private final String ID;
    private final LeaderEffect leaderEffect;
    private final Map<ResourceType, Integer> requirementsResources;
    private final List<CardType> requirementsCards;
    private boolean isActive;
    private final int victoryPoints;

    // Used only for test purposes
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

    // TODO DA CAMBIARE, ESPONE L'IMPLEMENTAZIONE
    public Map<ResourceType, Integer> getRequirementsResources() {
        return this.requirementsResources;
    }

    // TODO DA CAMBIARE, ESPONE L'IMPLEMENTAZIONE
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
                "\nStatus = "+activation()+
                "\nPoints =" + victoryPoints +
                '}'+ ANSI_Color.RESET;
    }

    private String activation() {
        if(isActive) return "active";
        else return "inactive";
    }

    public String toImage(){
        return ResourceLocator.retrieveLeaderCardImage(this.ID);
    }

}
