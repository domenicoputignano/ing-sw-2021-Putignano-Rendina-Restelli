package it.polimi.ingsw.Commons;

import java.io.Serializable;

/**
 * This enum contains all the possible effects of a {@link LeaderCard} of Maestri del Rinascimento board game.
 * It implements the {@link Serializable} interface in order to be sent through the network.
 */
public enum Effect implements Serializable {
    CMARBLE, SALES, EXTRADEPOT, EXTRAPRODUCTION
}
