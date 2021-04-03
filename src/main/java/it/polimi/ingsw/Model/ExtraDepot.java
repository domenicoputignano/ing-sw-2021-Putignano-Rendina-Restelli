package it.polimi.ingsw.Model;

public class ExtraDepot implements Depot {
    private int occ;
    private final ResourceType type;
    private final int size = 2;

    public ExtraDepot(int occ, ResourceType type) {
        this.occ = occ;
        this.type = type;
    }

    public void add(int num){
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
}
