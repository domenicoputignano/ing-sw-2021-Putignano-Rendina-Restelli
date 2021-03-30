package it.polimi.ingsw.Model;

import java.security.InvalidParameterException;
import java.util.EnumMap;
import java.util.Map;

public class Strongbox {
    private Map<ResourceType,Integer> resources;

    public Strongbox(Map<ResourceType, Integer> resources) {
        this.resources = resources;
    }

    public void addResources(Map<ResourceType,Integer> resources) throws InvalidParameterException
    {
        resources.forEach( (key,value)-> this.resources.merge(key,value,Integer::sum));
    }
    public void takeResources(Map<ResourceType,Integer> resources)
    {
        resources.forEach( (key,value)-> {
        if (this.resources.get(key) < resources.get(key)) {
            throw new InvalidParameterException(); //eventualmente definire diversa eccezione
        }
        }  );
        resources.forEach( (key,value)-> this.resources.merge(key,value,(v1,v2)-> v1-v2));
    }
}
