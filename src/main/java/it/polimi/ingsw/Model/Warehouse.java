package it.polimi.ingsw.Model;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Warehouse {
    private Map<ResourceType,Integer> avaiableResources;
    private NormalDepot[] normalDepots;
    private ExtraDepot[] extraDepots;
    private Strongbox strongbox;

    public Warehouse(Map<ResourceType, Integer> avaiableResources, NormalDepot[] normalDepot, ExtraDepot[] extraDepot, Strongbox strongbox) {
        this.avaiableResources = avaiableResources;
        this.normalDepots = normalDepot;
        this.extraDepots = extraDepot;
        this.strongbox = strongbox;
    }

    public boolean checkResources(Map<ResourceType,Integer> neededResources)
    {
        return neededResources.keySet().stream().allMatch((key) -> (neededResources.get(key) < this.avaiableResources.get(key)));
    }
    public void takeResources(Map<ResourceType,Integer> resources)
    {
        // prendere risorse dal mercato o dallo strongbox

    }
    public void addResourcesToStrongbox(Map<ResourceType,Integer> resources)
    {
        strongbox.addResources(resources);
        // UPDATE AVAIABLE RESOURCES
    }
    public void addResourcesToDepot(ResourceType type,int n)
    {
        // TODO
    }
    public void move(int depotInput,int depotOutput,int num)
    {
        // TODO
    }
    public void editWarehouse(Map<ResourceType,Integer> input,Map<ResourceType,Integer> output)
    {
        // TODO
    }

    public void updateAvaiableResources(){
        EnumMap<ResourceType, Integer> local = strongbox.getResources().clone();
        for(NormalDepot d : normalDepots){
            local.put(d.getType(), local.get(d.getType())+d.getOcc());
        }
        local.forEach(
                (key,value)-> Arrays.stream(extraDepots).filter(x->x!=null).collect(Collectors.toMap(x -> x.getType(), x -> x.getOcc())).merge(key,value,Integer::sum));
        this.avaiableResources = local;
    }

}
