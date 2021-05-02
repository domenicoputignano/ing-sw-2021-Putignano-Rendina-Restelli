package it.polimi.ingsw.Model;

import it.polimi.ingsw.Commons.StateFavorTiles;

import static it.polimi.ingsw.Commons.StateFavorTiles.*;

public class VaticanReportSection {
    private int popeFavorTiles;
    private int startSpace;
    private final int popeSpace;
    private StateFavorTiles state;

    public VaticanReportSection(int startSpace, int popeSpace, int popeFavorTiles){
        this.startSpace = startSpace;
        this.popeSpace = popeSpace;
        this.popeFavorTiles = popeFavorTiles;
        this.state = FACEDOWN;
    }

    public boolean isPopeSpace(int pos){
        if(pos == this.popeSpace) return true;
        return false;
    }

    public int getPopeSpace() {
        return popeSpace;
    }

    public void setValidFavorTiles(StateFavorTiles state){
        this.state = state;
    }

    public StateFavorTiles getState() {
        return state;
    }

    public int getStartSpace() {
        return startSpace;
    }
}
