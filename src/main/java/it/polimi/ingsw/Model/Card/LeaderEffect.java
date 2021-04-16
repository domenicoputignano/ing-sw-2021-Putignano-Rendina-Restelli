package it.polimi.ingsw.Model.Card;

import it.polimi.ingsw.Model.ResourceType;

public class LeaderEffect {
    private Effect effect;
    private ResourceType type;

    public LeaderEffect(Effect effect, ResourceType type){
        this.effect = effect;
        this.type = type;
    }

    public Effect getEffect() {
        return effect;
    }

    public ResourceType getType() {
        return type;
    }
}
