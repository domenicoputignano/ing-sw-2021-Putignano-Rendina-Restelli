package it.polimi.ingsw.Model;

public class NormalDepot implements Depot {
    private int occ;
    private ResourceType type;
    private final int size;

    public NormalDepot(int occ, ResourceType type, int size) {
        this.occ = occ;
        this.type = type;
        this.size = size;
    }

    public void add(int num)
    {
        // if(occ+num > this.size) eccezione
        this.occ+=num;
    }
    public void take(int num)
    {
        // if(occ-num < 0) eccezione
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
