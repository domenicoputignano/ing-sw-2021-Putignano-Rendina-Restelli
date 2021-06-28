package it.polimi.ingsw.model.marketTray;

import it.polimi.ingsw.commons.ResourceType;

public class Servant implements WhiteMarbleEffect {

    /**
     * Implements servant effect in strategy pattern.
     * @return a resource of servant type.
     * @see WhiteMarbleEffect
     */
    public ResourceType add() { return ResourceType.servant; }

}
