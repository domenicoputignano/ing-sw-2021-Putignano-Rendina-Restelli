package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.DepotNotFoundException;
import it.polimi.ingsw.Exceptions.DepotOutOfBoundsException;
import it.polimi.ingsw.Exceptions.IncompatibleResourceTypeException;
import it.polimi.ingsw.Exceptions.StrongboxOutOfBoundException;

import java.util.*;
import java.util.stream.Collectors;


public class Warehouse implements Cloneable {
    private Map<ResourceType,Integer> availableResources;
    private NormalDepot[] normalDepots;
    private ExtraDepot[] extraDepots;
    private Strongbox strongbox;

    public Warehouse(Map<ResourceType, Integer> availableResources, NormalDepot[] normalDepot, ExtraDepot[] extraDepot, Strongbox strongbox) {
        this.availableResources = availableResources;
        this.normalDepots = normalDepot;
        this.extraDepots = extraDepot;
        this.strongbox = strongbox;
    }

    public Warehouse()
    {
        this.availableResources = new EnumMap<ResourceType, Integer>(ResourceType.class);
        this.availableResources.put(ResourceType.servant,0);
        this.availableResources.put(ResourceType.coin,0);
        this.availableResources.put(ResourceType.shield,0);
        this.availableResources.put(ResourceType.stone,0);
        this.normalDepots = new NormalDepot[3];
        this.normalDepots[0] = new NormalDepot(1);
        this.normalDepots[1] = new NormalDepot(2);
        this.normalDepots[2] = new NormalDepot(3);
        this.extraDepots = new ExtraDepot[2];
        this.strongbox = new Strongbox();
    }


    public void initializeExtraDepot(ResourceType resourceType)
    {
        if(this.extraDepots[0] == null)
            this.extraDepots[0] = new ExtraDepot(resourceType);
        else
        if(this.extraDepots[1] == null)
            this.extraDepots[1] = new ExtraDepot(resourceType);
    }



    public void addResourcesToStrongbox(Map<ResourceType,Integer> resources)
    {
        strongbox.addResources(resources);
        this.updateAvailableResources();
    }



    /* -1 perché gli indici dati dalla CLI partono da 1*/
    public void addResourcesToDepot(int depotIndex, ResourceType type, int num) throws DepotOutOfBoundsException, IncompatibleResourceTypeException {
        if(!isValidEditing(type,depotIndex))
            throw new IncompatibleResourceTypeException();
        NormalDepot d = getNormalDepots()[depotIndex - 1];
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
        List<ExtraDepot> extraDepot = Arrays.stream(extraDepots).filter(x -> x!=null&&x.getType()==type).collect(Collectors.toList());
        if(extraDepot.size() == 0) throw new DepotNotFoundException();
        else extraDepot.get(0).add(num);
        this.updateAvailableResources();
    }


    public void takeResourceFromNormalDepot(ResourceType type, int occ) throws DepotNotFoundException, DepotOutOfBoundsException {
        int i = findNormalDepotByResourceType(type);
        normalDepots[i].take(occ);
    }

    public void takeResourcesFromStrongbox(Map<ResourceType, Integer> occurences) throws StrongboxOutOfBoundException {
        strongbox.takeResources(occurences);
    }

    public void takeResourceFromExtraDepot(ResourceType type, int occ) throws DepotNotFoundException, DepotOutOfBoundsException {
        int i = findExtraDepotByResourceType(type);
        extraDepots[i].take(occ);
    }


    public boolean checkResources(Map<ResourceType,Integer> neededResources)
    {
        return neededResources.keySet().stream().allMatch((key) -> (neededResources.get(key) <= this.availableResources.get(key)));
    }

    public boolean checkResourceFromNormalDepot(ResourceType type, int occ) {
        try {
            int i = findNormalDepotByResourceType(type);
            if(occ <= normalDepots[i].getOcc()) return true;
            else return false;
        } catch (DepotNotFoundException e) { return false; }
    }


    public boolean checkResourceFromStrongBox(ResourceType type, int occ) {
        return strongbox.getResources().get(type) <= occ;
    }

    public boolean checkResourceFromExtraDepot(ResourceType type, int occ) {
        try {
            int i = findExtraDepotByResourceType(type);
            if(occ <= extraDepots[i].getOcc()) return true;
            else return false;
        } catch(DepotNotFoundException e) { return false; }
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


    public boolean checkMoveFromNormalDepotToExtraDepot(int depotFrom,int occ, int extraDepotTo) {
        if(extraDepots[extraDepotTo-1] != null)
            if(normalDepots[depotFrom-1].getType() == extraDepots[extraDepotTo-1].getType()
                    && occ+extraDepots[extraDepotTo-1].getOcc()<=2
                    && normalDepots[depotFrom-1].getOcc()>=occ)
                return true;
        return false;
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


    public void moveFromNormalDepotToNormalDepot(int depotFrom, int depotTo) {
        ResourceType resourceTemp = normalDepots[depotTo].getType();
        int occTemp = normalDepots[depotTo].getOcc();
        int sizeTemp = normalDepots[depotTo].getSize();
        int sizeTempFrom = normalDepots[depotFrom].getSize();
        normalDepots[depotTo] = new NormalDepot(normalDepots[depotFrom].getOcc(),normalDepots[depotFrom].getType(),sizeTemp);
        normalDepots[depotFrom] = new NormalDepot(occTemp, resourceTemp, sizeTempFrom);
    }


    public void moveFromNormalDepotToExtraDepot(int depotFrom, int occ, int extraDepotTo) throws DepotOutOfBoundsException {
        normalDepots[depotFrom-1].take(occ);
        extraDepots[extraDepotTo-1].add(occ);
    }

    public void moveFromExtraDepotToNormalDepot(int extraDepotFrom, int occ, int depotTo) throws DepotOutOfBoundsException {
        extraDepots[extraDepotFrom-1].take(occ);
        if(normalDepots[depotTo-1].getType() == null)
            normalDepots[depotTo-1].setType(extraDepots[extraDepotFrom-1].getType());
        normalDepots[depotTo-1].add(occ);
    }


    public int findNormalDepotByResourceType(ResourceType type) throws DepotNotFoundException {
        for(int i = 0; i < normalDepots.length; i++) {
            if (normalDepots[i].getType() == type) return i;
        }
        throw new DepotNotFoundException();
    }

    public int findExtraDepotByResourceType(ResourceType type) throws DepotNotFoundException {
        for(int i = 0; i < extraDepots.length; i++) {
            if(extraDepots[i]!=null) {
               if(extraDepots[i].getType() == type) return i;
            }
        }
        throw new DepotNotFoundException();
    }



    private boolean isValidEditing(ResourceType resourceType, int depotTo)
    {
        for(NormalDepot depots: normalDepots)
            if(resourceType == depots.getType() && depots!=normalDepots[depotTo-1])
                return false;
        return true;
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
        this.availableResources = local;
    }


    public Map<ResourceType, Integer> getAvailableResources() {
        return availableResources;
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
