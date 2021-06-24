package it.polimi.ingsw.Model;

import it.polimi.ingsw.Client.reducedmodel.ReducedDepot;
import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Exceptions.DepotOutOfBoundsException;

import java.security.InvalidParameterException;


public class NormalDepot implements Depot {
    private int occ;
    private ResourceType type;
    private int size;

    public NormalDepot(int occ, ResourceType type, int size) {
        try {
            if(occ > size)
                throw new InvalidParameterException();
            this.occ = occ;
            this.type = type;
            this.size = size;
        }
        catch (InvalidParameterException e) {
           //ERRORE interno al server
        }

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
        if(this.occ == 0) type = null;
    }

    public int getOcc() {
        return occ;
    }


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

    public void setType(ResourceType type) {
        this.type = type;
    }

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
