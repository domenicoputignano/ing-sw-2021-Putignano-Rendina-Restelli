package it.polimi.ingsw.client.reducedmodel;

import it.polimi.ingsw.commons.ResourceType;

import java.io.Serializable;
import java.util.Objects;
/**
 * This class represents a simplified version of a depot with respect to the server's class.
 * It contains only the information required client side.
 */
public class ReducedDepot implements Serializable {
    /**
     * Attributes that represent the contents of the depot
     */
    private final int occ;
    private final ResourceType type;
    private final int size;
    /**
     * Initialize an instance by setting occurrences ,type and size of the depot.
     */
    public ReducedDepot(int occ, ResourceType type, int size) {
        this.occ = occ;
        this.type = type;
        this.size = size;
    }


    /**
     * method used to compare two reduced Depots
     * @param o reduced depot to compare
     * @return true if the depots are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReducedDepot that = (ReducedDepot) o;
        return occ == that.occ && size == that.size && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(occ, type, size);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < occ; i++ )
            result.append(type).append("\t");
        return String.valueOf(result);
    }

    public int getOcc() {
        return occ;
    }

    public ResourceType getType() {
        return type;
    }

    public int getSize() {
        return size;
    }
}
