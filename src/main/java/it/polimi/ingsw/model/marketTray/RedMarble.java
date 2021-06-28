package it.polimi.ingsw.model.marketTray;

import it.polimi.ingsw.commons.ColorMarble;
import it.polimi.ingsw.exceptions.NoSuchResourceTypeException;
import it.polimi.ingsw.commons.ResourceType;

/**
 * This class inherit from abstract class Marble because it's present in the market board.
 * It's characterized by red color and it hasn't any resource associated but it brings with it one faith point.
 */
public class RedMarble extends Marble {

    public RedMarble() {
        this.colorMarble = ColorMarble.RED;
    }

    /**
     * Implements the effect of this marble over player warehouse.
     * @throws NoSuchResourceTypeException because there's no standard resource associated
     */
    @Override
    public ResourceType addResources() throws NoSuchResourceTypeException {
        return super.addResources();
    }


}
