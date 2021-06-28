package it.polimi.ingsw.Utils;

import java.io.Serializable;

/**
 * Enum representing sources from which a player can pick resources to perform an action of the game.
 */
public enum ResourceSource implements Serializable {
    /**
     * DEPOT represents a {@link it.polimi.ingsw.Model.NormalDepot}
     * EXTRA represents an {@link it.polimi.ingsw.Model.ExtraDepot}
     * STRONGBOX represents a {@link it.polimi.ingsw.Model.Strongbox}
     */
    DEPOT,EXTRA,STRONGBOX
}
