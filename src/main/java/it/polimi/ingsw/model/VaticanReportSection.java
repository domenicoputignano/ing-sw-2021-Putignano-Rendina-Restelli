package it.polimi.ingsw.model;

import it.polimi.ingsw.commons.StateFavorTiles;

import static it.polimi.ingsw.commons.StateFavorTiles.*;

public class VaticanReportSection {
    /**
     * The number of victory points given if the tile associated with this section is turned up.
     */
    private final int popeFavorTiles;
    /**
     * The index of the faith cell where this vatican report section begins.
     */
    private final int startSpace;
    /**
     * The index of the faith cell where this vatican report section ends.
     * When a player hits this cell, a Vatican Report must be activated on this section.
     */
    private final int popeSpace;
    /**
     * The state of the favor tile associated with this section.
     */
    private StateFavorTiles state;

    /**
     * This custom constructor is used only for test purposes.
     * Original sections are instead loaded parsing them from an external json source.
     */
    public VaticanReportSection(int startSpace, int popeSpace, int popeFavorTiles){
        this.startSpace = startSpace;
        this.popeSpace = popeSpace;
        this.popeFavorTiles = popeFavorTiles;
        this.state = FACEDOWN;
    }

    /**
     * @param pos the position of player.
     * @return whether the player is on the pope space of this section.
     */
    public boolean isPopeSpace(int pos){
        return pos == this.popeSpace;
    }

    /**
     * Gets the victory points given to the player owner of the faith track by this section.
     * A Vatican Report Section gives victory points only if the associated Favor Tile is turned up.
     * @return the number of victory points given.
     */
    public int getVictoryPoints() {
        if (state == FACEUP)
            return popeFavorTiles;
        else return 0;
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
