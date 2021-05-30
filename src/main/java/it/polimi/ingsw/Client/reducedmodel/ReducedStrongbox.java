package it.polimi.ingsw.Client.reducedmodel;

import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Utils.ANSI_Color;

import java.io.Serializable;
import java.util.Map;

public class ReducedStrongbox implements Serializable {
    private final Map<ResourceType, Integer> resources;

    public ReducedStrongbox(Map<ResourceType, Integer> resources){
        this.resources = resources;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        resources.forEach((key, value) -> stringBuilder.append(ANSI_Color.escape(key)).
                append(key).append(" ").append(value).append(ANSI_Color.RESET).append("\n"));
        return String.valueOf(stringBuilder);
    }

}
