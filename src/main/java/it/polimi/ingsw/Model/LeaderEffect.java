package it.polimi.ingsw.Model;

public enum LeaderEffect {
    CMARBLE, SALES, EXTRADEPOT, EXTRAPRODUCTION;
    private ResourceType type;

    public ResourceType getType() {
        return type;
    }
}
