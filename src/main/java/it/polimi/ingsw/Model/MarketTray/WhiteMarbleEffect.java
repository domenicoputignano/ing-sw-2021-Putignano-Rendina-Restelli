package it.polimi.ingsw.Model.MarketTray;

import it.polimi.ingsw.Commons.ResourceType;


/**
 * This interface is used to change runtime behaviour of white marbles in taking resources from market.
 */
public interface WhiteMarbleEffect {

    /**
     * Method that return resource associated to the white marble that have been set by leader card.
    */
    public ResourceType add();

}