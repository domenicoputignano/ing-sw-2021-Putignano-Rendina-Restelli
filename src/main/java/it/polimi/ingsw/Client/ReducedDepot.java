package it.polimi.ingsw.Client;

import it.polimi.ingsw.Commons.ResourceType;

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
}
