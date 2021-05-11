package it.polimi.ingsw.Model.MarketTray;

import it.polimi.ingsw.Commons.ColorMarble;
import it.polimi.ingsw.Exceptions.NoSuchResourceTypeException;
import it.polimi.ingsw.Commons.ResourceType;

public class RedMarble extends Marble {

    public RedMarble() {
        this.colorMarble = ColorMarble.RED;
    }

    @Override
    public ResourceType addResources() throws NoSuchResourceTypeException {
        return super.addResources();
    }


}
