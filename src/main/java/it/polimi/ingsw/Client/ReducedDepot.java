package it.polimi.ingsw.Client;

import it.polimi.ingsw.Model.ResourceType;

import java.io.Serializable;

public class ReducedDepot implements Serializable {
    private final int occ;
    private final ResourceType type;
    private final int size;

    public ReducedDepot(int occ, ResourceType type, int size) {
        this.occ = occ;
        this.type = type;
        this.size = size;
    }
}
