package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.DepotNotFoundException;
import it.polimi.ingsw.Exceptions.DepotOutOfBoundsException;
import it.polimi.ingsw.Exceptions.IncompatibleResourceTypeException;

import java.util.*;
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

    public Warehouse()
    {
        this.avaiableResources = new EnumMap<ResourceType, Integer>(ResourceType.class);
        this.avaiableResources.put(ResourceType.servant,0);
        this.avaiableResources.put(ResourceType.coin,0);
        this.avaiableResources.put(ResourceType.shield,0);
        this.avaiableResources.put(ResourceType.stone,0);
        this.normalDepots = new NormalDepot[3];
        this.normalDepots[0] = new NormalDepot(1);
        this.normalDepots[1] = new NormalDepot(2);
        this.normalDepots[2] = new NormalDepot(3);
        this.extraDepots = new ExtraDepot[2];
        this.strongbox = new Strongbox();
    }
    public boolean checkResources(Map<ResourceType,Integer> neededResources)
    {
        return neededResources.keySet().stream().allMatch((key) -> (neededResources.get(key) <= this.avaiableResources.get(key)));
    }
    public void takeResources(Map<ResourceType,Integer> resources)
    {
        // prendere risorse dal mercato o dallo strongbox

    }
    public void addResourcesToStrongbox(Map<ResourceType,Integer> resources)
    {
        strongbox.addResources(resources);
        this.updateAvailableResources();
    }


    /*
    public void addResourcesToDepot(ResourceType type, int num) throws DepotNotFoundException, DepotOutOfBoundsException {
        List<NormalDepot> depot = Arrays.stream(normalDepots).filter(x -> x.getType()!=null&&x.getType()==type).collect(Collectors.toList());
        if(depot.size() == 0) throw new DepotNotFoundException();
        else depot.get(0).add(num);
        this.updateAvailableResources();
    }*/


    /* -1 perché gli indici dati dalla CLI partono da 1*/
    public void addResourcesToDepot(int depotindex, ResourceType type, int num) throws DepotOutOfBoundsException, IncompatibleResourceTypeException {
        NormalDepot d = getNormalDepots()[depotindex-1];
        if(d.getOcc() == 0) {
           d.setType(type);
           d.add(num);
       } else {
           if(d.getType()==type) {
               d.add(num);
           } else {
               throw new IncompatibleResourceTypeException();
           }
       }
        updateAvailableResources();
    }


    public void addResourcesToExtraDepot(ResourceType type, int num) throws DepotOutOfBoundsException, DepotNotFoundException {
        List<ExtraDepot> extradepot = Arrays.stream(extraDepots).filter(x -> x!=null&&x.getType()==type).collect(Collectors.toList());
        if(extradepot.size() == 0) throw new DepotNotFoundException();
        else extradepot.get(0).add(num);
        this.updateAvailableResources();
    }


    public void move(Depot from, Depot to,int num) throws DepotOutOfBoundsException {
        // TODO
    }


    private void updateAvailableResources(){
        EnumMap<ResourceType, Integer> local = strongbox.getResources().clone();
        for(NormalDepot d : normalDepots){
            if(d.getType()!=null)
                local.put(d.getType(), local.get(d.getType())+d.getOcc());
        }
        for(ExtraDepot e : extraDepots){
            if(e!=null)
            local.put(e.getType(), local.get(e.getType())+e.getOcc());
        }
        this.avaiableResources = local;

    }

    public Map<ResourceType, Integer> getAvaiableResources() {
        return avaiableResources;
    }

    public NormalDepot[] getNormalDepots() {
        return normalDepots;
    }

    public ExtraDepot[] getExtraDepots() {
        return extraDepots;
    }

    public Strongbox getStrongbox() {
        return strongbox;
    }
}
