package it.polimi.ingsw.Model.MarketTray;

import it.polimi.ingsw.Commons.ResourceType;

public class Stone implements WhiteMarbleEffect {

    /**
     * Implements stone effect in strategy pattern.
     * @return a resource of stone type.
     * @see WhiteMarbleEffect
     */
    public ResourceType add() { return ResourceType.stone; }

}