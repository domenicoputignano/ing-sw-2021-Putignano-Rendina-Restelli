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
}
