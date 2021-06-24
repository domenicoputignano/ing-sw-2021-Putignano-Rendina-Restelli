package it.polimi.ingsw.Model.MarketTray;

import it.polimi.ingsw.Commons.ResourceType;

public class Servant implements WhiteMarbleEffect {

    /**
     * Implements servant effect in strategy pattern.
     * @return a resource of servant type.
     * @see WhiteMarbleEffect
     */
    public ResourceType add() { return ResourceType.servant; }

}
