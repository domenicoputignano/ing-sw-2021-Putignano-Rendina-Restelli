package it.polimi.ingsw.utils;

import java.io.Serializable;

/**
 * Enum used to bind a destination to each marble gained during a {@link it.polimi.ingsw.model.TakeResourcesFromMarket} action.
 */
public enum MarbleDestination implements Serializable {
    DEPOT1, DEPOT2, DEPOT3, EXTRA, DISCARD, NOTNEEDED;
}
