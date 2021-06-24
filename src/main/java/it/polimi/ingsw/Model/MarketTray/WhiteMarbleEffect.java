package it.polimi.ingsw.Model.MarketTray;

import it.polimi.ingsw.Commons.ResourceType;


/**
 * This interface is used to implement a strategy pattern that allows white marble to encapsulate and change
 * their runtime behaviour while taking resources from market.
 */
public interface WhiteMarbleEffect {

    /**
     * Method that return resource associated to the white marble that have been set by leader card, different for each player.
    */
    ResourceType add();

}