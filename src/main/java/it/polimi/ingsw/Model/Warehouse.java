package it.polimi.ingsw.Model;

import it.polimi.ingsw.Client.reducedmodel.ReducedDepot;
import it.polimi.ingsw.Client.reducedmodel.ReducedStrongbox;
import it.polimi.ingsw.Client.reducedmodel.ReducedWarehouse;
import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Exceptions.DepotNotFoundException;
import it.polimi.ingsw.Exceptions.DepotOutOfBoundsException;
import it.polimi.ingsw.Exceptions.IncompatibleResourceTypeException;
import it.polimi.ingsw.Exceptions.StrongboxOutOfBoundException;
import it.polimi.ingsw.Utils.Pair;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class representing the actual warehouse in player board as an object made by a strongbox, three depots and
 * two possible extra depots (depending on player's active leader effects).
 */
public class Warehouse implements Cloneable {
    /**
     * Map with all the resources available in warehouse, summing of those present into depots, strongbox and extra depots.
     */
    private Map<ResourceType,Integer> availableResources;
    /**
     * Array with three instances of normal depot
     */
    private final NormalDepot[] normalDepots;
    /**
     * Array of two extra depots that are set to null until an extra depot effect becomes active
     * @see it.polimi.ingsw.Commons.LeaderEffect
     */
    private final ExtraDepot[] extraDepots;
    /**
     * Instance of a strongbox
     */
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


    /**
     * Initializes an extra depot
     * @param resourceType the immutable resource that can be stored in the extra depot
     * @see ExtraDepot
     */
    public void initializeExtraDepot(ResourceType resourceType)
    {
        if(this.extraDepots[0] == null)
            this.extraDepots[0] = new ExtraDepot(resourceType);
        else
        if(this.extraDepots[1] == null)
            this.extraDepots[1] = new ExtraDepot(resourceType);
    }


    /**
     * Stores resources into the strongbox and updates available resources.
     * @param resources map of resources with non-negative occurrences that have to be stored.
     */
    public void addResourcesToStrongbox(Map<ResourceType,Integer> resources)
    {
        strongbox.addResources(resources);
        this.updateAvailableResources();
    }

    /**
     * Stores a non-negative amount of resource in a normal depot and updates available resources.
     * @param depotIndex index of the required normal depot.
     * @param type kind of resource that has to be stored.
     * @param num occurrences to be stored.
     * @throws DepotOutOfBoundsException if this addition is not compliant with normal depot size
     * @throws IncompatibleResourceTypeException if we're attempting to add a type of resource different from the one that is already stored.
     */
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


    /**
     * Stores a positive number of resources in an extra depot, found on the basis of which type of resource the player wants to store in it and
     * updates available resources.
     * @param type type of resource to be stored.
     * @param num positive number of occurrences to be stored.
     * @throws DepotOutOfBoundsException if number of occurrences exceed the size of extra depot or it becomes negative.
     * @throws DepotNotFoundException if there is no extra depot available to store required type of resource.
     */
    public void addResourcesToExtraDepot(ResourceType type, int num) throws DepotOutOfBoundsException, DepotNotFoundException {
        List<ExtraDepot> extraDepot = Arrays.stream(extraDepots).filter(x -> x!=null&&x.getType()==type).collect(Collectors.toList());
        if(extraDepot.size() == 0) throw new DepotNotFoundException();
        else extraDepot.get(0).add(num);
        this.updateAvailableResources();
    }


    /**
     * Removes a non-negative number of occurrences from a normal depot found on the basis of which type of resource the player requires, at the end updates available resources.
     * @param type type of resource to be removed.
     * @param occ number of resource to be removed.
     * @throws DepotNotFoundException if there's no normal depot associated to the kind of resource required.
     * @throws DepotOutOfBoundsException if number of occurrences becomes negative.
     */
    public void takeResourceFromNormalDepot(ResourceType type, int occ) throws DepotNotFoundException, DepotOutOfBoundsException {
        int i = findNormalDepotByResourceType(type);
        normalDepots[i].take(occ);
        updateAvailableResources();
    }

    /**
     * Removes a map of non-negative occurrences from the strongbox and updates available resources.
     * @param occurrences map containing resources to be removed.
     * @throws StrongboxOutOfBoundException if at least of one number of occurrence of resource in the strongbox becomes negative.
     */
    public void takeResourcesFromStrongbox(Map<ResourceType, Integer> occurrences) throws StrongboxOutOfBoundException {
        strongbox.takeResources(occurrences);
        updateAvailableResources();
    }

    /**
     * Removes a non-negative number of occurrences from an extra depot found on the basis of resource type chosen, at the end updates available resources.
     * @param type type of resource to be removed.
     * @param occ number of resource to be removed.
     * @throws DepotNotFoundException if there is no extra depot that contains required type of resource.
     * @throws DepotOutOfBoundsException if number of occurrences in extra depot becomes negative.
     */
    public void takeResourceFromExtraDepot(ResourceType type, int occ) throws DepotNotFoundException, DepotOutOfBoundsException {
        int i = findExtraDepotByResourceType(type);
        extraDepots[i].take(occ);
        updateAvailableResources();
    }


    /**
     * Boolean method that checks if the player has enough resources to perform a certain action by comparing map of needed resources with available ones.
     * @param neededResources resources needed to do an action.
     * @return the result of the comparison.
     */
    public boolean checkResources(Map<ResourceType,Integer> neededResources)
    {
        return neededResources.keySet().stream().allMatch((key) -> (neededResources.get(key) <= this.availableResources.get(key)));
    }

    /**
     * Boolean method that checks if the warehouse has a normal depot with a non-negative number of a certain resource type.
     * @param type  resource type required.
     * @param occ number of resource type required.
     * @return the result of the check.
     */
    public boolean checkResourceFromNormalDepot(ResourceType type, int occ) {
        try {
            int i = findNormalDepotByResourceType(type);
            if(occ <= normalDepots[i].getOcc()) return true;
            else return false;
        } catch (DepotNotFoundException e) { return false; }
    }

    /**
     * Boolean method that checks if strongbox has enough occurrences of a certain resource type.
     * @param type resource type required.
     * @param occ number of occurrences.
     * @return the result of the check.
     */
    public boolean checkResourceFromStrongBox(ResourceType type, int occ) {
        if(strongbox.getResources().get(type) < occ) return false;
        else return true;
    }

    /**
     * Checks whether an extra depot has enough occurrences of a certain resource type.
     * @param type resource type required.
     * @param occ number of occurrences.
     * @return the result of the check.
     */
    public boolean checkResourceFromExtraDepot(ResourceType type, int occ) {
        try {
            int i = findExtraDepotByResourceType(type);
            if(occ <= extraDepots[i].getOcc()) return true;
            else return false;
        } catch(DepotNotFoundException e) { return false; }
    }


    /**
     * Boolean method called by turn controller over player warehouse to check whether a move action from normal depot
     * to another normal depot is possible or not on the basis of game rules.
     * @param depotFrom index of starting normal depot.
     * @param depotTo index of destination normal depot.
     * @return the result of the check.
     */
    public boolean checkMoveFromNormalDepotToNormalDepot(int depotFrom, int depotTo) {
        //checks if starting depot is not empty
        if(normalDepots[depotFrom - 1].getType() == null) {
            return false;
        }
        else {
            if(normalDepots[depotTo - 1].getType() == null) {
                //checks if destination depot has less occurrences than starting one
                if(normalDepots[depotTo - 1].getSize() < normalDepots[depotFrom - 1].getOcc()) return false;
                else return true;
            }
            else
            {
                //checks if it is possible to switch resources between normal depots
                if(normalDepots[depotTo - 1].getOcc() > normalDepots[depotFrom - 1].getSize() ||
                        normalDepots[depotFrom - 1].getOcc() > normalDepots[depotTo - 1].getSize()) return false;
                else return true;
            }
        }
    }

    /**
     * Boolean method called by turn controller to check if a move action can be performed from a normal depot to an extra depot
     * comparing number of occurrences and type of resources to be moved.
     * @param depotFrom index of starting normal depot.
     * @param occ number of occurrences that have to be moved.
     * @param extraDepotTo index of extra depot destination.
     * @return the result of the check.
     */
    public boolean checkMoveFromNormalDepotToExtraDepot(int depotFrom,int occ, int extraDepotTo) {
        if(extraDepots[extraDepotTo-1] != null)
            if(normalDepots[depotFrom-1].getType() == extraDepots[extraDepotTo-1].getType()
                    && occ+extraDepots[extraDepotTo-1].getOcc()<=2
                    && normalDepots[depotFrom-1].getOcc()>=occ)
                return true;
        return false;
    }

    /**
     * Boolean method called by turn controller to check if a move action can be performed from an extra depot to a normal one
     * comparing number of occurrences and ensuring that the action is compliant with game rules.
     * @param extraDepotFrom index of starting extra depot.
     * @param occ number of occurrences that have to be moved.
     * @param depotTo index of normal depot destination.
     * @return the result of the check.
     */
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

    /**
     * Moves resources from a normal depot to another normal depot.
     * It switches the number of occurrences and the resource type creating two new instances of normal depots.
     * Each time this method is called it creates a new instances for depots involved into the move.
     * @param depotFrom index of starting normal depot
     * @param depotTo index of destination normal depot.
     * @see it.polimi.ingsw.Utils.MoveFromNormalToNormalAction
     */
    public void moveFromNormalDepotToNormalDepot(int depotFrom, int depotTo) {
        ResourceType resourceTemp = normalDepots[depotTo - 1].getType();
        int occTemp = normalDepots[depotTo - 1].getOcc();
        int sizeTemp = normalDepots[depotTo - 1].getSize();
        int sizeTempFrom = normalDepots[depotFrom - 1].getSize();
        normalDepots[depotTo - 1] = new NormalDepot(normalDepots[depotFrom - 1].getOcc(),normalDepots[depotFrom - 1].getType(),sizeTemp);
        normalDepots[depotFrom - 1] = new NormalDepot(occTemp, resourceTemp, sizeTempFrom);
    }

    /**
     * Moves resources from a normal depot to an extra one. Each time this method is called it creates a new instances for
     * depots involved into the move.
     * @param depotFrom index of normal depot from which the resources are moved.
     * @param occ number of occurrences to be moved.
     * @param extraDepotTo index of extra depot destination for the resources.
     * @see it.polimi.ingsw.Utils.MoveFromNormalToExtraAction
     */
    public void moveFromNormalDepotToExtraDepot(int depotFrom, int occ, int extraDepotTo) {
        NormalDepot normalDepot = normalDepots[depotFrom-1];
        normalDepots[depotFrom-1] = new NormalDepot(normalDepot.getOcc()-occ, normalDepot.getType(), normalDepot.getSize());
        if(normalDepots[depotFrom-1].getOcc() == 0) normalDepots[depotFrom-1].setType(null);
        ExtraDepot extraDepot = extraDepots[extraDepotTo-1];
        extraDepots[extraDepotTo-1] = new ExtraDepot(extraDepot.getOcc()+occ, extraDepot.getType());
    }

    /**
     * Moves resources from an extra depot to a normal one. Each time this method is called it creates a new instances for
     * depots involved into the move.
     * @param extraDepotFrom index of extra depot from which the resources are moved.
     * @param occ number of occurrences to be moved.
     * @param depotTo index of normal depot destination for the resources.
     * @see it.polimi.ingsw.Utils.MoveFromExtraToNormalAction
     */
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

    /**
     * Method that searches for a normal depot given a resource type.
     * @param type resource type that has to be linked to extra depot to find.
     * @return index of the normal depot (if present).
     * @throws DepotNotFoundException if the player doesn't have any normal depot with the required resource type
     */
    public int findNormalDepotByResourceType(ResourceType type) throws DepotNotFoundException {
        for(int i = 0; i < normalDepots.length; i++) {
            if (normalDepots[i].getType() == type) return i;
        }
        throw new DepotNotFoundException();
    }

    /**
     * Method that searches for an extra depot given a resource type.
     * @param type resource type that has to be linked to extra depot to find.
     * @return index of extra depot (if present).
     * @throws DepotNotFoundException if the player doesn't have any extra depot with the required resource type
     */
    public int findExtraDepotByResourceType(ResourceType type) throws DepotNotFoundException {
        for(int i = 0; i < extraDepots.length; i++) {
            if(extraDepots[i]!=null) {
                if(extraDepots[i].getType() == type) return i;
            }
        }
        throw new DepotNotFoundException();
    }

    /**
     * This method calculates number of points gained summing all the resources in warehouse and dividing the result by five.
     * @return the number of points.
     */
    public int calcVictoryPointsWarehouse()
    {
        return availableResources.keySet().stream().map(x -> availableResources.get(x)).reduce(0,Integer::sum)/5;
    }

    /**
     * This method checks whether an action over normal depots breaks game rules, in particular way it ensures that at most one
     * normal depot contains a particular type of resource.
     * @param resourceType type of resource.
     * @param depotTo index of the depot to be edited.
     * @return the result of the check.
     */
    private boolean isValidEditing(ResourceType resourceType, int depotTo)
    {
        for(NormalDepot depots: normalDepots)
            if(resourceType == depots.getType() && depots!=normalDepots[depotTo-1])
                return false;
        return true;
    }

    /**
     * Updates map representing all the resources available in warehouse. It collects them starting from
     * strongbox and then for each one sums occurrences coming from depots with the previous value.
     */
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


    /**
     * Unsafe getter method used for test purposes only.
     */
    public ExtraDepot[] getterUnsafeExtraDepots() {
        return extraDepots;
    }

    public ReducedWarehouse getReducedVersion(){
        ReducedDepot[] reducedNormalDepots = Arrays.stream(normalDepots).map(NormalDepot::getReducedVersion).toArray(ReducedDepot[]::new);
        ReducedDepot[] reducedExtraDepots = new ReducedDepot[2];

        for(int i = 0; i < extraDepots.length; i++) {
            if (extraDepots[i] != null)
                reducedExtraDepots[i] = extraDepots[i].getReducedVersion();
        }
        ReducedStrongbox reducedStrongbox = strongbox.getReducedVersion();
        return new ReducedWarehouse(reducedNormalDepots, reducedExtraDepots, reducedStrongbox, availableResources);
    }

    /**
     * Unsafe getter method used for test purposes only.
     */
    public Map<ResourceType, Integer> getAvailableResources() {
        return availableResources;
    }

    /**
     * @return for each Resource Type, the number of occurrences present in the strongbox.
     */
    public List<Pair<ResourceType, Integer>> getResourcesInStrongbox() {
        List<Pair<ResourceType,Integer>> result = new ArrayList<>(4);
        strongbox.getResources().forEach((key,value) -> result.add(new Pair<>(key, value)));
        return result;
    }

}
