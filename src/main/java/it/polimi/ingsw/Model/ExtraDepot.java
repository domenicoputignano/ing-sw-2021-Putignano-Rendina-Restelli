package it.polimi.ingsw.Model;

import it.polimi.ingsw.Client.reducedmodel.ReducedDepot;
import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Exceptions.DepotOutOfBoundsException;
import org.codehaus.plexus.component.annotations.Requirement;

/**
 * Class that represent a particular implementation of Depot interface.
 * It allows the player to do same things available with normal depot, such as storing and picking resources.
 * Main difference is that an extra depot has a fixed size of two occurrences and it can store a single kind of resource all game long.
 */
public class ExtraDepot implements Depot {
    /**
     * Number of resources stored.
     */
    private int occ;
    /**
     * Kind of resource that can be stored.
     */
    private final ResourceType type;
    /**
     * Number of resources that can be stored.
     */
    private final int size = 2;

    /**
     * Constructor that initializes an empty extra depot.
     * @param type kind of resource that can be stored.
     */
    public ExtraDepot(ResourceType type) {
        this.type = type;
        this.occ = 0;
    }

    /**
     * Constructor that initializes a non-empty extra depot with a certain amount of occurrences
     * @param occ non negative number of occurrences that has to be less or equals to two.
     * @param type kind of resource stored
     */
    public ExtraDepot(int occ, ResourceType type) {
        this.occ = occ;
        this.type = type;
    }

    /**
     * Stores a non-negative number of resource
     * @param num occurrences to store in the extra depot
     * @throws DepotOutOfBoundsException if occurrences exceed the size of extra depot
     */
    @Override
    public void add(int num)throws DepotOutOfBoundsException {
        if(occ+num > this.size) throw new DepotOutOfBoundsException();
        this.occ+=num;
    }

    /**
     * Removes a non-negative number of resources.
     * @param num occurrences that have to be removed from extra depot.
     * @throws DepotOutOfBoundsException if occurrences number become negative.
     */
    @Override
    public void take(int num) throws DepotOutOfBoundsException {
        if(occ-num < 0) throw new DepotOutOfBoundsException();
        this.occ-=num;
    }

    /**
     * Getters of object attributes
     */
    @Override
    public int getOcc() {
        return occ;
    }

    @Override
    public ResourceType getType() {
        return type;
    }

    @Override
    public int getSize() {
        return size;
    }

    /**
     * Builds a simplified version of an extra depot that is serializable, exchanged over the network and used client side.
     */
    @Override
    public ReducedDepot getReducedVersion(){
        return new ReducedDepot(this.occ, this.type, this.size);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExtraDepot that = (ExtraDepot) o;
        return occ == that.occ && size == that.size && type == that.type;
    }

}
