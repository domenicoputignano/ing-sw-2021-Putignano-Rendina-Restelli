package it.polimi.ingsw.Model;

import java.security.InvalidParameterException;
import java.util.Map;

public class Warehouse {
    private Map<ResourceType,Integer> avaiableResources;
    private Depot[] normalDepot;
    private Depot[] extraDepot;
    private Strongbox strongbox;

    public Warehouse(Map<ResourceType, Integer> avaiableResources, Depot[] normalDepot, Depot[] extraDepot, Strongbox strongbox) {
        this.avaiableResources = avaiableResources;
        this.normalDepot = normalDepot;
        this.extraDepot = extraDepot;
        this.strongbox = strongbox;
    }

    public boolean checkResources(Map<ResourceType,Integer> neededResources)
    {
        return neededResources.keySet().stream().allMatch((key) -> (neededResources.get(key) < this.avaiableResources.get(key)));
    }
    public void takeResources(Map<ResourceType,Integer> resources)
    {
        resources.forEach( (key,value)-> this.avaiableResources.merge(key,value,(v1,v2) -> v1-v2));
    }
    public void addResourcesToStrongbox(Map<ResourceType,Integer> resources)
    {
        // TODO
    }
    public void addResourcesToDepot(ResourceType type,int n)
    {
        // TODO
    }
    public void move(int depotInput,int depotOutput,int num)
    {
        // TODO
    }
    public void moveToExtraDepot(int depotInput,int depotExtraOutput,int num)
    {
        // TODO
    }
    public void editWarehouse(Map<ResourceType,Integer> input,Map<ResourceType,Integer> output)
    {
        // TODO
    }

}
