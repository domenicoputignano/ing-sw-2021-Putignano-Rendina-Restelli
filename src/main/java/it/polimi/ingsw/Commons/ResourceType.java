package it.polimi.ingsw.Commons;

import it.polimi.ingsw.Utils.ANSI_Color;

import java.io.Serializable;

/**
 * This enum represents the set of standard resources that can be stored in player's warehouse or extra depots.
 */
public enum ResourceType implements Serializable {
    coin, shield, servant, stone;

    /**
     * This method returns a String representation of a resource used by command line interface to display it.
     */
    public String toString() {
        return ANSI_Color.escape(this);
    }

}
