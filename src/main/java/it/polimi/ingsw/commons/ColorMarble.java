package it.polimi.ingsw.commons;

import java.io.Serializable;

/**
 * This enum contains all the possible colors of the Marbles of the MarketTray.
 * It implements the Serializable interface in order to be sent through the network.
 */

public enum ColorMarble implements Serializable {
    GREY, RED, YELLOW, WHITE, BLUE, PURPLE
}
