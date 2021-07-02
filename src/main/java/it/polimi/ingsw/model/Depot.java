package it.polimi.ingsw.model;

import it.polimi.ingsw.client.reducedmodel.ReducedDepot;
import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.exceptions.DepotOutOfBoundsException;

/**
 * This interface represents a generic depot of Maestri del Rinascimento board game.
 * A depot can stock {@link ResourceType} occurrences. The resources contained in a depot must be only
 * of the {@link ResourceType} associated with the depot itself. A depot has a limited size.
 * There are two types of Depots : {@link NormalDepot} and {@link ExtraDepot}.
 * This interface condenses the common methods of both implementations.
 */

public interface Depot {
    void add(int num) throws DepotOutOfBoundsException;
    void take(int num) throws DepotOutOfBoundsException;
    ResourceType getType();
    int getOcc();
    int getSize();
    ReducedDepot getReducedVersion();
}