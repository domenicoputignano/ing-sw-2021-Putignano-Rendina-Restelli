package it.polimi.ingsw.Client;

import it.polimi.ingsw.Commons.StateFavorTiles;

public class ReducedFaithTrack {
    private final int faithMarker;
    private final int victoryPoints[] = new int[25];
    private final StateFavorTiles[] favorTiles;

    public ReducedFaithTrack(int faithMarker, StateFavorTiles[] favorTiles) {
        this.faithMarker = faithMarker;
        this.favorTiles = favorTiles;
    }
}
