package it.polimi.ingsw.Client;

import it.polimi.ingsw.Commons.ResourceType;

import java.util.Map;

public class ReducedStrongbox {
    private final Map<ResourceType, Integer> resources;

    public ReducedStrongbox(Map<ResourceType, Integer> resources){
        this.resources = resources;
    }
}
