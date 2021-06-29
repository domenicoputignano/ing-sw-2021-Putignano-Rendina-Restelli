package it.polimi.ingsw.client.reducedmodel;

import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.utils.ANSI_Color;

import java.io.Serializable;
import java.util.Map;
/**
 * This class represents a simplified version of a Strongbox with respect to the server's class.
 * It contains only the information required client side.
 */
public class ReducedStrongbox implements Serializable {
    /**
     * Attributes that represent the contents of the Strongbox
     */
    private final Map<ResourceType, Integer> resources;
    /**
     * Initialize an instance by setting resources of the Strongbox.
     */
    public ReducedStrongbox(Map<ResourceType, Integer> resources){
        this.resources = resources;
    }

    public int getOccurrencesOfResource(ResourceType resource){
        return resources.get(resource);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        resources.forEach((key, value) -> stringBuilder.append(ANSI_Color.escape(key)).
                append(" ").append(value).append(ANSI_Color.RESET).append("\n"));
        return String.valueOf(stringBuilder);
    }

}
