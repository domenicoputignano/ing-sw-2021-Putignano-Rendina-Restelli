package it.polimi.ingsw.Commons;

import java.io.Serializable;

public enum StateFavorTiles implements Serializable {
    FACEUP, FACEDOWN, DISCARDED;

    public String getTileState(int tileIndex) {
        if (this.equals(FACEDOWN)||this.equals(DISCARDED)) return "x    !";
        else return ""+(tileIndex+2)+"    !";
    }
}
