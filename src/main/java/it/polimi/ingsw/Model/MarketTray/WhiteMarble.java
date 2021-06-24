package it.polimi.ingsw.Model.MarketTray;

import it.polimi.ingsw.Commons.ColorMarble;
import it.polimi.ingsw.Exceptions.NoSuchResourceTypeException;
import it.polimi.ingsw.Commons.ResourceType;

import java.util.Objects;

/**
 * This class represents a particular kind of marble that doesn't have an immutable resource type associated but
 * it can change at runtime with regard to the strategy pattern.
 */
public class WhiteMarble extends Marble {

    /**
     * Attribute that represent the effect of a white marble over player's warehouse or extradepot
     */
    private WhiteMarbleEffect effect = null;

    public WhiteMarble() {
        this.colorMarble = ColorMarble.WHITE;
    }

    public void setEffect(WhiteMarbleEffect effect) {
        this.effect = effect;
    }


    /**
     * @return
     * @throws NoSuchResourceTypeException
     */
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
