package it.polimi.ingsw.Client.reducedmodel;

import it.polimi.ingsw.Commons.StateFavorTiles;

import java.io.Serializable;

public class ReducedFaithTrack implements Serializable {
    private final int faithMarker;
    private final int victoryPoints[] = new int[25];
    private final StateFavorTiles[] favorTiles;

    public ReducedFaithTrack(int faithMarker, StateFavorTiles[] favorTiles) {
        this.faithMarker = faithMarker;
        this.favorTiles = favorTiles;
    }
}
