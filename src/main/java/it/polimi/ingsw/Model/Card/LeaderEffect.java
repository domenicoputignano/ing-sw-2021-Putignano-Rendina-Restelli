package it.polimi.ingsw.Model.Card;

import it.polimi.ingsw.Model.ResourceType;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeaderEffect that = (LeaderEffect) o;
        return effect == that.effect && type == that.type;
    }

}
