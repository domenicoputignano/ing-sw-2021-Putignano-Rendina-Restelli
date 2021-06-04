package it.polimi.ingsw.Commons;

import it.polimi.ingsw.Utils.ANSI_Color;

import java.io.Serializable;

public enum ResourceType implements Serializable {
    coin, shield, servant, stone;

    public String toString() {
        return ANSI_Color.escape(this);
    }

}
