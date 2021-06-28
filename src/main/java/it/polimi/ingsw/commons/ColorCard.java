package it.polimi.ingsw.commons;

import java.io.Serializable;

/**
 * This enum contains all the possible colors of a development card.
 * It implements the Serializable interface in order to be sent through the network.
 */

public enum ColorCard implements Serializable {
    green, blue, purple, yellow
}
