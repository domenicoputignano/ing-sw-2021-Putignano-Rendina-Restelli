package it.polimi.ingsw.Client.reducedmodel;

import it.polimi.ingsw.Commons.ResourceType;

import java.io.Serializable;
import java.util.Map;

public class ReducedStrongbox implements Serializable {
    private final Map<ResourceType, Integer> resources;

    public ReducedStrongbox(Map<ResourceType, Integer> resources){
        this.resources = resources;
    }
}
