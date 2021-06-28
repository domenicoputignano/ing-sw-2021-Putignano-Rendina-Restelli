package it.polimi.ingsw.model.marketTray;

import it.polimi.ingsw.commons.ColorMarble;
import it.polimi.ingsw.commons.ResourceType;

/**
 * This class represents a marble that could be gray, blue, purple or yellow. What makes a standard marble different from a white one is
 * the fact that it cannot change the resource associated itself and different from a red one is that resource associated can be stored
 * only in warehouse's depots.
 */
public class StandardMarble extends Marble {

    /**
     * Immutable attribute representing resource associated to te marble
     */
    private final ResourceType type;

    /**
     * Constructor that initializes a standard marble with its color and resource.
     */
    public StandardMarble(ColorMarble colorMarble, ResourceType type) {
        this.colorMarble = colorMarble;
        this.type = type;
    }

    /**
     * Returns resource associated to the standard marble and it's used while adding resources to player's depots or extradepots.
     */
    public ResourceType addResources() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StandardMarble that = (StandardMarble) o;
        return type == that.type;
    }

}
