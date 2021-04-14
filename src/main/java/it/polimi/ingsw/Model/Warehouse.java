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
        if(!isValidEditing(type,depotindex))
            throw new IncompatibleResourceTypeException();
        NormalDepot d = getNormalDepots()[depotindex - 1];
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

    public void initializeExtraDepot(ResourceType resourceType)
    {
        if(this.extraDepots[0] == null)
            this.extraDepots[0] = new ExtraDepot(resourceType);
        else
            if(this.extraDepots[1] == null)
                this.extraDepots[1] = new ExtraDepot(resourceType);
    }


    // controller needs to know if he can perform moving action
    public boolean checkMoveFromNormalDepotToNormalDepot(int depotFrom, int depotTo) {
        if(normalDepots[depotFrom].getType() == null) {
            return false;
        }
        else {
            if(normalDepots[depotTo].getType() == null) {
                if(normalDepots[depotTo].getSize() < normalDepots[depotFrom].getOcc()) return false;
                else return true;
            }
            else
            {
                //caso in cui devo switchare le risorse tra i due depot
                if(normalDepots[depotTo].getOcc() > normalDepots[depotFrom].getSize() ||
                        normalDepots[depotFrom].getOcc() > normalDepots[depotTo].getSize()) return false;
                else return true;
            }
        }
    }

    public void moveFromNormalDepotToNormalDepot(int depotFrom, int depotTo) {
        ResourceType resourceTemp = normalDepots[depotTo].getType();
        int occTemp = normalDepots[depotTo].getOcc();
        int sizeTemp = normalDepots[depotTo].getSize();
        int sizeTempFrom = normalDepots[depotFrom].getSize();
        normalDepots[depotTo] = new NormalDepot(normalDepots[depotFrom].getOcc(),normalDepots[depotFrom].getType(),sizeTemp);
        normalDepots[depotFrom] = new NormalDepot(occTemp, resourceTemp, sizeTempFrom);
    }

    public boolean checkMoveFromNormalDepotToExtraDepot(int depotFrom,int occ, int extraDepotTo) {
        if(extraDepots[extraDepotTo-1] != null)
            if(normalDepots[depotFrom-1].getType() == extraDepots[extraDepotTo-1].getType()
                    && occ+extraDepots[extraDepotTo-1].getOcc()<=2
                    && normalDepots[depotFrom-1].getOcc()>=occ)
                return true;
        return false;
    }

    public void moveFromNormalDepotToExtraDepot(int depotFrom,int occ, int extraDepotTo) throws DepotOutOfBoundsException {
        normalDepots[depotFrom-1].take(occ);
        extraDepots[extraDepotTo-1].add(occ);
    }

    public boolean checkMoveFromExtraDepotToNormalDepot(int extraDepotFrom, int occ, int depotTo) throws IncompatibleResourceTypeException {
        if(extraDepots[extraDepotFrom-1] != null) {
            if (!isValidEditing(extraDepots[extraDepotFrom - 1].getType(),depotTo))
                throw new IncompatibleResourceTypeException();
            if ((normalDepots[depotTo - 1].getType() == extraDepots[extraDepotFrom - 1].getType()
                    || normalDepots[depotTo - 1].getType() == null)
                    && normalDepots[depotTo - 1].getOcc() + occ <= normalDepots[depotTo - 1].getSize()
                    && extraDepots[extraDepotFrom - 1].getOcc() >= occ)
                return true;
            }
        return false;
    }

    public void moveFromExtraDepotToNormalDepot(int extraDepotFrom, int occ, int depotTo) throws DepotOutOfBoundsException {
        extraDepots[extraDepotFrom-1].take(occ);
        if(normalDepots[depotTo-1].getType() == null)
            normalDepots[depotTo-1].setType(extraDepots[extraDepotFrom-1].getType());
        normalDepots[depotTo-1].add(occ);
    }

    private boolean isValidEditing(ResourceType resourceType, int depotTo)
    {
        for(NormalDepot depots: normalDepots)
            if(resourceType == depots.getType() && depots!=normalDepots[depotTo-1])
                return false;
        return true;
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
