package it.polimi.ingsw.model.marketTray;

import it.polimi.ingsw.commons.ResourceType;

public class Stone implements WhiteMarbleEffect {

    /**
     * Implements stone effect in strategy pattern.
     * @return a resource of stone type.
     * @see WhiteMarbleEffect
     */
    public ResourceType add() { return ResourceType.stone; }

}