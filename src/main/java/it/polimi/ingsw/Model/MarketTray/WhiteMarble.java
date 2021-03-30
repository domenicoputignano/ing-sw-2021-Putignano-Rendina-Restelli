package it.polimi.ingsw.Model.MarketTray;

import it.polimi.ingsw.Model.ResourceType;

public class WhiteMarble extends Marble {

    private WhiteMarbleEffect effect = null;

    public WhiteMarble() {
        this.color = Color.WHITE;
    }

    public void setEffect(WhiteMarbleEffect effect) {
        this.effect = effect;
    }

    public ResourceType addResources() {
        return effect.add();
    }

    public WhiteMarbleEffect getEffect() {
        return effect;
    }

}
