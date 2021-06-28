package it.polimi.ingsw.model.marketTray;

import it.polimi.ingsw.commons.ResourceType;


public class Coin implements WhiteMarbleEffect {
    /**
     * Implements coin effect in strategy pattern.
     * @return a resource of coin type.
     * @see WhiteMarbleEffect
     */
    public ResourceType add() { return ResourceType.coin; }

}
