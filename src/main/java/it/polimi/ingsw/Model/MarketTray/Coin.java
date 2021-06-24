package it.polimi.ingsw.Model.MarketTray;

import it.polimi.ingsw.Commons.ResourceType;


public class Coin implements WhiteMarbleEffect {
    /**
     * Implements coin effect in strategy pattern.
     * @return a resource of coin type.
     * @see WhiteMarbleEffect
     */
    public ResourceType add() { return ResourceType.coin; }

}
