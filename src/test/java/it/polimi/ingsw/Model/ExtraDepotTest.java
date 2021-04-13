package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.DepotOutOfBoundsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExtraDepotTest {

    ExtraDepot depot = new ExtraDepot(ResourceType.shield);
    ExtraDepot depot1 = new ExtraDepot(2,ResourceType.shield);

    @Test
    void initialize() {
        assertEquals(depot.getOcc(), 0);
        assertSame(depot.getType(), ResourceType.shield);
        assertEquals(depot.getSize(), 2);
        assertEquals(depot1.getOcc(), 2);
        assertSame(depot1.getType(), ResourceType.shield);
        assertEquals(depot1.getSize(), 2);
    }

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
        ExtraDepot depotClone = new ExtraDepot(2,depot.getType());
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