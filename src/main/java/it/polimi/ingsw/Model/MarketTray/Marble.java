package it.polimi.ingsw.Model.MarketTray;

import it.polimi.ingsw.Exceptions.NoSuchResourceTypeException;
import it.polimi.ingsw.Model.ResourceType;

public abstract class Marble {

    protected Color color;

    public ResourceType addResources() throws NoSuchResourceTypeException {
        throw new NoSuchResourceTypeException();
    }

    public Color getColor() { return color; }

}
