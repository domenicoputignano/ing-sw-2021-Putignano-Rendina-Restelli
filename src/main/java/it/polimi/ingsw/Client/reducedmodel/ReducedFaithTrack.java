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

    public String toString() {
        StringBuilder resultBuilder = new StringBuilder();
        for(int i = 0; i < 2; i++) {
            for(int j = 0; j < 25; j++) {
                if(i == 0) {
                    if (j == faithMarker) resultBuilder.append("*\t");
                    else resultBuilder.append("__\t");
                } else {
                    resultBuilder.append(j).append("\t");
                }
            }
            resultBuilder.append("\n");
        }
        return resultBuilder.toString();
    }

    public int getFaithMarker() {
        return faithMarker;
    }
}
