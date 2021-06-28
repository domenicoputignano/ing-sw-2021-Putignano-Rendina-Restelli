package it.polimi.ingsw.model;

import it.polimi.ingsw.client.reducedmodel.ReducedStrongbox;
import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.exceptions.StrongboxOutOfBoundException;

import java.security.InvalidParameterException;
import java.util.EnumMap;
import java.util.Map;

/**
 * Class used to represents a strongbox in player board. It can store an unlimited number of standard resources.
 */
public class Strongbox {
    /**
     * Map representing the resources contained in the strongbox
     */
    private final EnumMap<ResourceType,Integer> resources;

    public Strongbox() {
        this.resources = new EnumMap<>(ResourceType.class);
        this.resources.put(ResourceType.coin,0);
        this.resources.put(ResourceType.shield,0);
        this.resources.put(ResourceType.servant,0);
        this.resources.put(ResourceType.stone,0);
    }

    /**
     * Puts resources obtained after activating a production into the strongbox merging them with map of resources
     * already owned.
     * @param resources that have to be stored
     * @throws InvalidParameterException if the map that as to be merged is not compliant with the strongbox.
     */
    public void addResources(Map<ResourceType,Integer> resources) throws InvalidParameterException {
        resources.forEach( (key,value)-> this.resources.merge(key,value,Integer::sum));
    }

    /**
     * This method removes a certain quantity of resources from the strongbox
     * @param resources map of resources that has to be taken from the strongbox
     * @throws StrongboxOutOfBoundException if the action requires more than how much is owned for at least one kind of resource
     */
    public void takeResources(Map<ResourceType,Integer> resources) throws StrongboxOutOfBoundException {
        if(resources.keySet().stream().anyMatch( (key) -> resources.get(key) > this.resources.get(key))) throw new
                StrongboxOutOfBoundException();
        resources.forEach( (key,value)-> this.resources.merge(key,value,(v1,v2)-> v1-v2));
    }

    /**
     * Produces a simplified version of strongbox used by client.
     */
    public ReducedStrongbox getReducedVersion(){
        return new ReducedStrongbox(resources.clone());
    }

    /**
     * Returns the map representing resources currently owned.
     */
    public Map<ResourceType, Integer> getResources() {
        return resources;
    }
}
