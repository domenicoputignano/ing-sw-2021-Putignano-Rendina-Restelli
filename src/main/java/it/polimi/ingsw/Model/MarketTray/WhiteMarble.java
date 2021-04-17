package it.polimi.ingsw.Model.MarketTray;

import it.polimi.ingsw.Exceptions.NoSuchResourceTypeException;
import it.polimi.ingsw.Model.ResourceType;

public class WhiteMarble extends Marble {

    private WhiteMarbleEffect effect = null;

    public WhiteMarble() {
        this.color = Color.WHITE;
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

}
