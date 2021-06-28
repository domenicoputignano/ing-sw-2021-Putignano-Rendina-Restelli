package it.polimi.ingsw.commons;

import it.polimi.ingsw.utils.ResourceLocator;

import java.io.Serializable;
import java.util.Objects;

/**
 * This class represents a leader effect of a Leader Card of Maestri del Rinascimento board game.
 * It implements the {@link Serializable} interface in order to be sent through the network.
 * A Leader Effect is composed of an {@link Effect} and a {@link ResourceType} associated.
 */

public class LeaderEffect implements Serializable {
    /**
     * The {@link Effect} of this Leader Effect.
     */
    private final Effect effect;
    /**
     * The {@link ResourceType} of this LeaderEffect.
     */
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

    /**
     * @return a string representation of this Leader Effect.
     */
    @Override
    public String toString() {
        return "{" +
                "" + effect +
                ", type=" + type +
                '}';
    }

    /**
     * Returns the image representing the Leader Effect by calling the {@link ResourceLocator} class
     * in order to retrieve the image.
     * @return the string of the path to the image.
     */
    public String toImage() {
        return ResourceLocator.retrieveLeaderEffect(this);
    }
}
