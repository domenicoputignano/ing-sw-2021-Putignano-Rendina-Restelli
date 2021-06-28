package it.polimi.ingsw.client.reducedmodel;

import it.polimi.ingsw.commons.StateFavorTiles;

import java.io.Serializable;

public class ReducedFaithTrack implements Serializable {
    private final int faithMarker;
    private final int victoryPoints[] = new int[25];
    private final StateFavorTiles[] favorTiles;

    public ReducedFaithTrack(int faithMarker, StateFavorTiles[] favorTiles) {
        this.faithMarker = faithMarker;
        this.favorTiles = favorTiles;
    }


    @Override
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

    public String getFaithMarkerCell(int cellIndex) {
        if(cellIndex != faithMarker) return "     |";
        else {
            return "  *  |";
        }
    }

    public StateFavorTiles getStateFavorTile(int vatican_index)
    {
        return favorTiles[vatican_index-1];
    }

    public String getFavorTile(int tileIndex) {
        return favorTiles[tileIndex].getTileState(tileIndex);
    }

    public int getFaithMarker() {
        return faithMarker;
    }
}
