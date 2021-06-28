package it.polimi.ingsw.commons;

import java.io.Serializable;

/**
 * This enum contains all the possible states of a Favor Tiles of Maestri del Rinascimento board game.
 */
public enum StateFavorTiles implements Serializable {
    FACEUP, FACEDOWN, DISCARDED;

    /**
     * @return a string drawing the state of a Favor Tile.
     */
    public String getTileState(int tileIndex) {
        if (this.equals(FACEDOWN)||this.equals(DISCARDED)) return "x    !";
        else return ""+(tileIndex+2)+"    !";
    }
}
