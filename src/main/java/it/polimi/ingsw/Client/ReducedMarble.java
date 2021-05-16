package it.polimi.ingsw.Client;

import it.polimi.ingsw.Commons.ColorMarble;

import java.io.Serializable;

public class ReducedMarble implements Serializable {
    private final ColorMarble colorMarble;

    public ReducedMarble(ColorMarble colorMarble) {
        this.colorMarble = colorMarble;
    }

    public ColorMarble getColorMarble() {
        return colorMarble;
    }
}
