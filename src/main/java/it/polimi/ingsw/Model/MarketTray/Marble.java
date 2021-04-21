package it.polimi.ingsw.Model.MarketTray;

import it.polimi.ingsw.Exceptions.NoSuchResourceTypeException;
import it.polimi.ingsw.Model.ResourceType;

import java.util.Objects;

public abstract class Marble {

    protected Color color;

    public ResourceType addResources() throws NoSuchResourceTypeException {
        throw new NoSuchResourceTypeException();
    }

    public Color getColor() { return color; }

    @Override
    public String toString() {
        return "Marble{" +
                "color=" + color +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Marble marble = (Marble) o;
        return color == marble.color;
    }

}
