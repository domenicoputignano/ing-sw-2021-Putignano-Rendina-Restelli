package it.polimi.ingsw.Client.reducedmodel;

import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Utils.ANSI_Color;

import java.io.Serializable;
import java.util.Objects;

public class ReducedDepot implements Serializable {
    private final int occ;
    private final ResourceType type;
    private final int size;

    public ReducedDepot(int occ, ResourceType type, int size) {
        this.occ = occ;
        this.type = type;
        this.size = size;
    }



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
        result.append(size).append(": ");
        for(int i = 0; i < occ; i++ )
            result.append(type.toString()).append("\t");
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
