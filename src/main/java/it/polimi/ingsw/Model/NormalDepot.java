package it.polimi.ingsw.Model;

import it.polimi.ingsw.Client.reducedmodel.ReducedDepot;
import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Exceptions.DepotOutOfBoundsException;

/**
 * This class represents the default type of depot of Maestri del Rinascimento board game.
 * It allows the player to stock a limited number of a type of resources.
 */
public class NormalDepot implements Depot {
    /**
     * The number of resources stocked in the depot.
     */
    private int occ;
    /**
     * The type of {@link ResourceType} stocked in the depot.
     */
    private ResourceType type;
    /**
     * The maximum number of resources that can be stocked into the depot.
     */
    private final int size;

    /**
     * Construct a normal depot from the given occurrences, resource type and size.
     */
    public NormalDepot(int occ, ResourceType type, int size) {
        if(occ > size) {occ = size;}
        this.occ = occ;
        this.type = type;
        this.size = size;
    }

    /**
     * Constructs an empty normal depot of the given size.
     * @param size of the normal depot to construct.
     */
    public NormalDepot(int size) {
        this.occ=0;
        this.size=size;
    }

    /**
     * Adds the specified number of resources to this depot.
     * @param num the number of resources to add
     * @throws DepotOutOfBoundsException if the depot exceeds his size.
     */
    public void add(int num) throws DepotOutOfBoundsException {
        if(occ+num > this.size) throw new DepotOutOfBoundsException();
        this.occ+=num;
    }

    /**
     * Takes the specified number of resources from this depot.
     * @param num the number of resources to take.
     * @throws DepotOutOfBoundsException if the depot occurrences goes below zero.
     */
    public void take(int num) throws DepotOutOfBoundsException {
        if(occ-num < 0) throw new DepotOutOfBoundsException();
        this.occ-=num;
        if(this.occ == 0) type = null;
    }

    public int getOcc() {
        return occ;
    }

    /**
     * Empties the depot and clears the type associated.
     */
    public void clear() {
        this.occ = 0;
        this.type = null;
    }

    public ResourceType getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    /**
     * Sets the {@link ResourceType} associated with this depot.
     * @param type the {@link ResourceType} to associate with this depot.
     */
    public void setType(ResourceType type) {
        this.type = type;
    }

    /**
     * Gets a reduced version of this depot, that can be sent through the network in order to update the reduced
     * model client side.
     * @return the reduced version of this depot.
     */
    public ReducedDepot getReducedVersion() {
        return new ReducedDepot(this.occ, this.type, this.size);
    }

    @Override
    public String toString() {
        return ("Resource = "+ type +" Occurences = "+ occ +" Size = "+size);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NormalDepot that = (NormalDepot) o;
        return occ == that.occ && size == that.size && type == that.type;
    }


}
