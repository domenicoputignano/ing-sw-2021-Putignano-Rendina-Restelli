package it.polimi.ingsw.Model.MarketTray;

import it.polimi.ingsw.Commons.ResourceType;


public class Shield implements WhiteMarbleEffect {

    /**
     * Implements shield effect in strategy pattern.
     * @return a resource of shield type.
     * @see WhiteMarbleEffect
     */
    public ResourceType add() { return ResourceType.shield; }

}
