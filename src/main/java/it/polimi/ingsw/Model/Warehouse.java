package it.polimi.ingsw.Model;

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
        return true;
    }
    public void takeResources(Map<ResourceType,Integer> resources)
    {
        resources.forEach( (key,value)-> this.avaiableResources.merge(key,value,(v1,v2)-> v1-v2));
    }
    public void addResourcesToStrongbox(Map<ResourceType,Integer> resources)
    {

    }
    public void addResourcesToDepot(ResourceType type,int n)
    {

    }
    public void move(int depotInput,int depotOutput,int num)
    {

    }
    public void moveToExtraDepot(int depotInput,int depotExtraOutput,int num)
    {

    }
    public void editWarehouse(Map<ResourceType,Integer> input,Map<ResourceType,Integer> output)
    {

    }

}
