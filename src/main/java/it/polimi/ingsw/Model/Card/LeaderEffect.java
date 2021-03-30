package it.polimi.ingsw.Model.Card;

import it.polimi.ingsw.Model.ResourceType;

public enum LeaderEffect {
    CMARBLE, SALES, EXTRADEPOT, EXTRAPRODUCTION;
    private ResourceType type;

    public ResourceType getType() {
        return type;
    }
}
