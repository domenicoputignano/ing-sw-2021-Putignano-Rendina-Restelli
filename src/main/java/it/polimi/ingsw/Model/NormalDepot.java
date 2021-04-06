package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.DepotOutOfBoundsException;

public class NormalDepot implements Depot {
    private int occ;
    private ResourceType type;
    private final int size;

    public NormalDepot(int occ, ResourceType type, int size) {
        this.occ = occ;
        this.type = type;
        this.size = size;
    }
    public NormalDepot(int size)
    {
        this.occ=0;
        this.size=size;
    }
    public void add(int num) throws DepotOutOfBoundsException {
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

    public void setType(ResourceType type) {
        this.type = type;
    }
}
