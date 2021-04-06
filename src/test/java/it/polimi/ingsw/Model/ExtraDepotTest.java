package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.DepotOutOfBoundsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExtraDepotTest {

    ExtraDepot depot = new ExtraDepot(ResourceType.shield);

    @Test
    void add() throws DepotOutOfBoundsException {
        ExtraDepot depotClone = new ExtraDepot(depot.getType());
        depot.add(1);
        assertEquals(depotClone.getOcc()+1,depot.getOcc());
    }

    @Test
    void addException() throws DepotOutOfBoundsException {
        assertThrows(DepotOutOfBoundsException.class,()->depot.add(4));
    }

    @Test
    void take() throws DepotOutOfBoundsException {
        depot.add(2);
        ExtraDepot depotClone = new ExtraDepot(depot.getOcc(), depot.getType());
        depot.take(1);
        assertEquals(depotClone.getOcc()-1,depot.getOcc());
    }

    @Test
    void takeException() throws DepotOutOfBoundsException {
        depot.add(2);
        ExtraDepot depotClone = new ExtraDepot(depot.getOcc(), depot.getType());
        assertThrows(DepotOutOfBoundsException.class,()->depot.take(3));
    }

}