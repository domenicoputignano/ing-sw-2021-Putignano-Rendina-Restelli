package it.polimi.ingsw.Model.MarketTray;

import it.polimi.ingsw.Exceptions.NoSuchResourceTypeException;
import it.polimi.ingsw.Model.ResourceType;

public class RedMarble extends Marble {

    public RedMarble() {
        this.color = Color.RED;
    }

    @Override
    public ResourceType addResources() throws NoSuchResourceTypeException {
        return super.addResources();
    }


}
