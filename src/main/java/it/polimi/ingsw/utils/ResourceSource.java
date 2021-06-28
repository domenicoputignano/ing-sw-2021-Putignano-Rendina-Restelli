package it.polimi.ingsw.utils;

import java.io.Serializable;

/**
 * Enum representing sources from which a player can pick resources to perform an action of the game.
 */
public enum ResourceSource implements Serializable {
    /**
     * DEPOT represents a {@link it.polimi.ingsw.model.NormalDepot}
     * EXTRA represents an {@link it.polimi.ingsw.model.ExtraDepot}
     * STRONGBOX represents a {@link it.polimi.ingsw.model.Strongbox}
     */
    DEPOT,EXTRA,STRONGBOX
}
