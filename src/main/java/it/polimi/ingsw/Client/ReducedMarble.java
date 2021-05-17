package it.polimi.ingsw.Client;

import it.polimi.ingsw.Commons.ColorMarble;

import java.io.Serializable;
import java.util.Objects;

public class ReducedMarble implements Serializable {
    private final ColorMarble colorMarble;

    public ReducedMarble(ColorMarble colorMarble) {
        this.colorMarble = colorMarble;
    }

    public ColorMarble getColorMarble() {
        return colorMarble;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReducedMarble that = (ReducedMarble) o;
        return colorMarble == that.colorMarble;
    }

    @Override
    public int hashCode() {
        return Objects.hash(colorMarble);
    }

    @Override
    public String toString() {
        return " [" + colorMarble +']';
    }
}
