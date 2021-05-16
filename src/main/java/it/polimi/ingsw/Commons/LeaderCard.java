package it.polimi.ingsw.Commons;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class LeaderCard implements Serializable {
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
        if(requirementsCards.size()>0) return "{" +
                "\nEffect=" + leaderEffect +
                ",\nrequirementsCards=" + requirementsCards +
                ",\nactive=" + isActive +
                ",\nvictoryPoints=" + victoryPoints +
                '}';
        else return "{" +
                "\nEffect=" + leaderEffect +
                ",\nrequirementsResources=" + requirementsResources +
                ",\nactive=" + isActive +
                ",\nvictoryPoints=" + victoryPoints +
                '}';
    }
}
