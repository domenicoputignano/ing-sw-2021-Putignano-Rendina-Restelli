package it.polimi.ingsw.Model;

import it.polimi.ingsw.Client.ReducedDepot;
import it.polimi.ingsw.Exceptions.DepotOutOfBoundsException;

public interface Depot {
    void add(int num) throws DepotOutOfBoundsException;
    void take(int num) throws DepotOutOfBoundsException;
    ResourceType getType();
    int getOcc();
    int getSize();
    ReducedDepot getReducedVersion();
}
