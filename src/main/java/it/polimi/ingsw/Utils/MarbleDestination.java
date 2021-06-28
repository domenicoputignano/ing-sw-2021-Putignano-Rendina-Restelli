package it.polimi.ingsw.Utils;

import java.io.Serializable;

/**
 * Enum used to bind a destination to each marble gained during a {@link it.polimi.ingsw.Model.TakeResourcesFromMarket} action.
 */
public enum MarbleDestination implements Serializable {
    DEPOT1, DEPOT2, DEPOT3, EXTRA, DISCARD, NOTNEEDED;
}
