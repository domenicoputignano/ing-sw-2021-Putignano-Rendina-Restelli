package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.DepotOutOfBoundsException;


public class ExtraDepot implements Depot {
    private int occ;
    private final ResourceType type;
    private final int size = 2;

    public ExtraDepot(ResourceType type) {
        this.type = type;
        this.occ = 0;
    }

    public ExtraDepot(int occ, ResourceType type) {
        this.occ = occ;
        this.type = type;
    }

    public void add(int num)throws DepotOutOfBoundsException {
        if(occ+num > this.size) throw new DepotOutOfBoundsException();
        this.occ+=num;
    }

    public void take(int num) throws DepotOutOfBoundsException {
        if(occ-num < 0) throw new DepotOutOfBoundsException();
        this.occ-=num;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExtraDepot that = (ExtraDepot) o;
        return occ == that.occ && size == that.size && type == that.type;
    }

}
