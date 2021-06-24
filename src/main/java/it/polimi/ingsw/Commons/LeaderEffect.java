package it.polimi.ingsw.Commons;

import it.polimi.ingsw.Utils.ResourceLocator;

import java.io.Serializable;
import java.util.Objects;

public class LeaderEffect implements Serializable {
    private final Effect effect;
    private final ResourceType type;

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
    public int hashCode() {
        return Objects.hash(effect, type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeaderEffect that = (LeaderEffect) o;
        return effect == that.effect && type == that.type;
    }

    @Override
    public String toString() {
        return "{" +
                "" + effect +
                ", type=" + type +
                '}';
    }

    public String toImage() {
        return ResourceLocator.retrieveLeaderEffect(this);
    }
}
