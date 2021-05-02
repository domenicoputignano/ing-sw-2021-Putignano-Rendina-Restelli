package it.polimi.ingsw.Model.MarketTray;

import it.polimi.ingsw.Commons.ColorMarble;
import it.polimi.ingsw.Exceptions.NoSuchResourceTypeException;
import it.polimi.ingsw.Model.ResourceType;

import java.util.Objects;

public class WhiteMarble extends Marble {

    private WhiteMarbleEffect effect = null;

    public WhiteMarble() {
        this.colorMarble = ColorMarble.WHITE;
    }

    public void setEffect(WhiteMarbleEffect effect) {
        this.effect = effect;
    }


    public ResourceType addResources() throws NoSuchResourceTypeException {
        if(effect!=null) return effect.add();
        return super.addResources();
    }

    public WhiteMarbleEffect getEffect() {
        return effect;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        WhiteMarble that = (WhiteMarble) o;
        return Objects.equals(effect, that.effect);
    }
}
