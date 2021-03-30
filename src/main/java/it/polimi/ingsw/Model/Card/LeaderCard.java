package it.polimi.ingsw.Model.Card;

import it.polimi.ingsw.Model.ResourceType;

import java.util.Map;

public class LeaderCard {
    private LeaderEffect effect;
    private Map<ResourceType, Integer> requirementsResources;
    private Map<CardType, Integer> requirementsCards;
    private boolean isActive;
    private int victoryPoints;

    public LeaderEffect getEffect() {
        return effect;
    }

    public Map<ResourceType, Integer> getRequirementsResources() {
        return requirementsResources;
    }

    public Map<CardType, Integer> getRequirementsCards() {
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
}
