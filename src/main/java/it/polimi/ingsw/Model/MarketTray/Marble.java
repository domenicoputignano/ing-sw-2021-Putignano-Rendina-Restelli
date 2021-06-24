package it.polimi.ingsw.Model.MarketTray;

import it.polimi.ingsw.Client.reducedmodel.ReducedMarble;
import it.polimi.ingsw.Commons.ColorMarble;
import it.polimi.ingsw.Exceptions.NoSuchResourceTypeException;
import it.polimi.ingsw.Commons.ResourceType;

/**
 * Basic representation of a marble. It allows all its subclasses to be contained in market board.
 */
public abstract class Marble {

    protected ColorMarble colorMarble;


    /**
     * Returns resource associated to the marble while adding it to the depots.
     */
    public ResourceType addResources() throws NoSuchResourceTypeException {
        throw new NoSuchResourceTypeException();
    }

    public ColorMarble getColor() { return colorMarble; }

    @Override
    public String toString() {
        return "Marble{" +
                "color=" + colorMarble +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Marble marble = (Marble) o;
        return colorMarble == marble.colorMarble;
    }


    /**
     * This method return a simplified instance of the marble.
     */
    public ReducedMarble getReducedVersion() {
        return new ReducedMarble(colorMarble);
    }

}
