package it.polimi.ingsw.Model.MarketTray;

import it.polimi.ingsw.Model.ResourceType;

public class StandardMarble extends Marble {

    private ResourceType type;

    public StandardMarble(Color color, ResourceType type) {
        this.color = color;
        this.type = type;
    }

    public ResourceType addResources() {
        return type;
    }

}
