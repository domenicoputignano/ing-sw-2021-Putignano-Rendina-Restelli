package it.polimi.ingsw.Model.Card;

import it.polimi.ingsw.Model.ResourceType;

import java.util.List;
import java.util.Map;

public class LeaderCard {
    private LeaderEffect leaderEffect;
    private Map<ResourceType, Integer> requirementsResources;
    private List<CardType> requirementsCards;
    private boolean isActive;
    private int victoryPoints;

    public LeaderCard(LeaderEffect leaderEffect, Map<ResourceType, Integer> requirementsResources, List<CardType> requirementsCards, int victoryPoints) {
        this.leaderEffect = leaderEffect;
        this.requirementsResources = requirementsResources;
        this.requirementsCards = requirementsCards;
        this.isActive = false;
        this.victoryPoints = victoryPoints;
    }

    public LeaderEffect getLeaderEffect() {
        return leaderEffect;
    }

    public Map<ResourceType, Integer> getRequirementsResources() {
        return requirementsResources;
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
}
