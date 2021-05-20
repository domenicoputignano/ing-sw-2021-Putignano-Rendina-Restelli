package it.polimi.ingsw.Model;

import it.polimi.ingsw.Client.reducedmodel.ReducedStrongbox;
import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Exceptions.StrongboxOutOfBoundException;

import java.security.InvalidParameterException;
import java.util.EnumMap;
import java.util.Map;

public class Strongbox {
    private EnumMap<ResourceType,Integer> resources;

    public Strongbox() {
        this.resources = new EnumMap<ResourceType, Integer>(ResourceType.class);
        this.resources.put(ResourceType.coin,0);
        this.resources.put(ResourceType.shield,0);
        this.resources.put(ResourceType.servant,0);
        this.resources.put(ResourceType.stone,0);
    }

    public void addResources(Map<ResourceType,Integer> resources) throws InvalidParameterException {
        resources.forEach( (key,value)-> this.resources.merge(key,value,Integer::sum));
    }


    public void takeResources(Map<ResourceType,Integer> resources) throws StrongboxOutOfBoundException {
        if(resources.keySet().stream().anyMatch( (key) -> resources.get(key) > this.resources.get(key))) throw new
                StrongboxOutOfBoundException();
        resources.forEach( (key,value)-> this.resources.merge(key,value,(v1,v2)-> v1-v2));
    }

    public ReducedStrongbox getReducedVersion(){
        return new ReducedStrongbox(resources.clone());
    }

    public Map<ResourceType, Integer> getResources() {
        return resources;
    }
}
