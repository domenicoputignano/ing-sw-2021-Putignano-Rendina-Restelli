package it.polimi.ingsw.model.marketTray;

import it.polimi.ingsw.commons.ColorMarble;
import it.polimi.ingsw.exceptions.NoSuchResourceTypeException;
import it.polimi.ingsw.commons.ResourceType;

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
     * Method that converts the marble to the associated resource depending on the runtime effect associated.
     * @return the resource type the marble has been converted to.
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
