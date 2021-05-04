package it.polimi.ingsw.Model;

import it.polimi.ingsw.Client.ReducedDepot;
import it.polimi.ingsw.Client.ReducedStrongbox;
import it.polimi.ingsw.Client.ReducedWarehouse;
import it.polimi.ingsw.Exceptions.DepotNotFoundException;
import it.polimi.ingsw.Exceptions.DepotOutOfBoundsException;
import it.polimi.ingsw.Exceptions.IncompatibleResourceTypeException;
import it.polimi.ingsw.Exceptions.StrongboxOutOfBoundException;
import it.polimi.ingsw.Utils.Pair;

import java.util.*;
import java.util.stream.Collectors;


public class Warehouse implements Cloneable {
    private Map<ResourceType,Integer> availableResources;
    private final NormalDepot[] normalDepots;
    private final ExtraDepot[] extraDepots;
    private final Strongbox strongbox;

    public Warehouse(Map<ResourceType, Integer> availableResources, NormalDepot[] normalDepot, ExtraDepot[] extraDepot, Strongbox strongbox) {
        this.availableResources = availableResources;
        this.normalDepots = normalDepot;
        this.extraDepots = extraDepot;
        this.strongbox = strongbox;
    }

    public Warehouse()
    {
        this.availableResources = new EnumMap<>(ResourceType.class);
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
        NormalDepot d = this.normalDepots[depotIndex - 1];
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
        updateAvailableResources();
    }

    public void takeResourcesFromStrongbox(Map<ResourceType, Integer> occurences) throws StrongboxOutOfBoundException {
        strongbox.takeResources(occurences);
        updateAvailableResources();
    }

    public void takeResourceFromExtraDepot(ResourceType type, int occ) throws DepotNotFoundException, DepotOutOfBoundsException {
        int i = findExtraDepotByResourceType(type);
        extraDepots[i].take(occ);
        updateAvailableResources();
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
        if(strongbox.getResources().get(type) < occ) return false;
        else return true;
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
        if(normalDepots[depotFrom - 1].getType() == null) {
            return false;
        }
        else {
            if(normalDepots[depotTo - 1].getType() == null) {
                if(normalDepots[depotTo - 1].getSize() < normalDepots[depotFrom - 1].getOcc()) return false;
                else return true;
            }
            else
            {
                //caso in cui devo switchare le risorse tra i due depot
                if(normalDepots[depotTo - 1].getOcc() > normalDepots[depotFrom - 1].getSize() ||
                        normalDepots[depotFrom - 1].getOcc() > normalDepots[depotTo - 1].getSize()) return false;
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



    public boolean checkMoveFromExtraDepotToNormalDepot(int extraDepotFrom, int occ, int depotTo){
        if(extraDepots[extraDepotFrom-1] != null && (isValidEditing(extraDepots[extraDepotFrom - 1].getType(),depotTo))) {
            if ((normalDepots[depotTo - 1].getType() == extraDepots[extraDepotFrom - 1].getType()
                    || normalDepots[depotTo - 1].getType() == null)
                    && normalDepots[depotTo - 1].getOcc() + occ <= normalDepots[depotTo - 1].getSize()
                    && extraDepots[extraDepotFrom - 1].getOcc() >= occ)
                return true;
        }
        return false;
    }


    public void moveFromNormalDepotToNormalDepot(int depotFrom, int depotTo) {
        ResourceType resourceTemp = normalDepots[depotTo - 1].getType();
        int occTemp = normalDepots[depotTo - 1].getOcc();
        int sizeTemp = normalDepots[depotTo - 1].getSize();
        int sizeTempFrom = normalDepots[depotFrom - 1].getSize();
        normalDepots[depotTo - 1] = new NormalDepot(normalDepots[depotFrom - 1].getOcc(),normalDepots[depotFrom - 1].getType(),sizeTemp);
        normalDepots[depotFrom - 1] = new NormalDepot(occTemp, resourceTemp, sizeTempFrom);
    }


    public void moveFromNormalDepotToExtraDepot(int depotFrom, int occ, int extraDepotTo) {
        NormalDepot normalDepot = normalDepots[depotFrom-1];
        normalDepots[depotFrom-1] = new NormalDepot(normalDepot.getOcc()-occ, normalDepot.getType(), normalDepot.getSize());
        if(normalDepots[depotFrom-1].getOcc() == 0) normalDepots[depotFrom-1].setType(null);
        ExtraDepot extraDepot = extraDepots[extraDepotTo-1];
        extraDepots[extraDepotTo-1] = new ExtraDepot(extraDepot.getOcc()+occ, extraDepot.getType());
    }

    public void moveFromExtraDepotToNormalDepot(int extraDepotFrom, int occ, int depotTo) {
        ExtraDepot extraDepot = extraDepots[extraDepotFrom-1];
        extraDepots[extraDepotFrom-1] = new ExtraDepot(extraDepot.getOcc()-occ, extraDepot.getType());
        NormalDepot normalDepot = normalDepots[depotTo-1];
        if(normalDepot.getType() == null){
            normalDepots[depotTo-1] = new NormalDepot(normalDepot.getOcc()+occ, extraDepot.getType(), normalDepot.getSize());
        } else{
            normalDepots[depotTo-1] = new NormalDepot(normalDepot.getOcc()+occ, normalDepot.getType(), normalDepot.getSize());
        }
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
        EnumMap<ResourceType, Integer> local = ((EnumMap<ResourceType, Integer>) strongbox.getResources()).clone();
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

    public List<NormalDepot> getNormalDepots() {
        List<NormalDepot> normalDepots = new ArrayList<>();
        for (NormalDepot normalDepot : this.normalDepots)
            normalDepots.add(new NormalDepot(normalDepot.getOcc(), normalDepot.getType(), normalDepot.getSize()));
        return normalDepots;
    }

    public List<ExtraDepot> getExtraDepots() {
        List<ExtraDepot> extraDepots = new ArrayList<>();
        for(int i=0;i<this.extraDepots.length && this.extraDepots[i]!=null; i++)
            extraDepots.add(new ExtraDepot(this.extraDepots[i].getOcc(),this.extraDepots[i].getType()));
        return extraDepots;
    }


    //TODO da eliminare
    public ExtraDepot[] getterUnsafeExtraDepots() {
        return extraDepots;
    }

    public ReducedWarehouse getReducedVersion(){
        ReducedDepot[] reducedNormalDepots = Arrays.stream(normalDepots).map(NormalDepot::getReducedVersion).toArray(ReducedDepot[]::new);
        ReducedDepot[] reducedExtraDepots = new ReducedDepot[2];

        for(int i = 0; i < extraDepots.length; i++)
            if(extraDepots[i]!=null)
                reducedExtraDepots[i] = extraDepots[i].getReducedVersion();

        ReducedStrongbox reducedStrongbox = strongbox.getReducedVersion();
        return new ReducedWarehouse(reducedNormalDepots, reducedExtraDepots, reducedStrongbox);
    }


    //TODO rivedere questi metodi che servono per il testing

    public Map<ResourceType, Integer> getAvailableResources() {
        //TODO modificare in modo da esporre solo una copia
        return availableResources;
    }

    public List<Pair<ResourceType, Integer>> getResourcesInStrongbox() {
        List<Pair<ResourceType,Integer>> result = new ArrayList<>(4);
        strongbox.getResources().forEach((key,value) -> result.add(new Pair<>(key, value)));
        return result;
    }

}
