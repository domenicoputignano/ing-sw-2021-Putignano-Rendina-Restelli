package it.polimi.ingsw.model.marketTray;

import it.polimi.ingsw.commons.ResourceType;


public class Shield implements WhiteMarbleEffect {

    /**
     * Implements shield effect in strategy pattern.
     * @return a resource of shield type.
     * @see WhiteMarbleEffect
     */
    public ResourceType add() { return ResourceType.shield; }

}
