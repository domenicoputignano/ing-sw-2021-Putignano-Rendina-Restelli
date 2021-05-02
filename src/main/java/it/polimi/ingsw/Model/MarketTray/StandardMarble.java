package it.polimi.ingsw.Model.MarketTray;

import it.polimi.ingsw.Commons.ColorMarble;
import it.polimi.ingsw.Model.ResourceType;

public class StandardMarble extends Marble {

    private ResourceType type;

    public StandardMarble(ColorMarble colorMarble, ResourceType type) {
        this.colorMarble = colorMarble;
        this.type = type;
    }

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
