package it.polimi.ingsw.model;

import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.exceptions.DepotOutOfBoundsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Test class that tests the correct functioning of the ExtraDepot class and its functionalities
 */
class ExtraDepotTest {

    ExtraDepot depot = new ExtraDepot(ResourceType.shield);
    ExtraDepot depot1 = new ExtraDepot(2,ResourceType.shield);
    /**
     * test method that tests the correct functioning of the Extra Depot initialization
     */
    @Test
    void initialize() {
        assertEquals(depot.getOcc(), 0);
        assertSame(depot.getType(), ResourceType.shield);
        assertEquals(depot.getSize(), 2);
        assertEquals(depot1.getOcc(), 2);
        assertSame(depot1.getType(), ResourceType.shield);
        assertEquals(depot1.getSize(), 2);
    }

    /**
     * test method that tests the correct functioning of adding resources to the extra depot
     */
    @Test
    void add() throws DepotOutOfBoundsException {
        ExtraDepot depotClone = new ExtraDepot(depot.getType());
        depot.add(1);
        assertEquals(depotClone.getOcc()+1,depot.getOcc());
    }

    /**
     * test method that tests for any exception that may occur in the action
     * of adding resources to the extra depot if it is full
     */
    @Test
    void addException() throws DepotOutOfBoundsException {
        assertThrows(DepotOutOfBoundsException.class,()->depot.add(4));
    }
    /**
     * test method that tests the correct functioning of take resources from the extra depot
     */
    @Test
    void take() throws DepotOutOfBoundsException {
        depot.add(2);
        ExtraDepot depotClone = new ExtraDepot(2,depot.getType());
        depot.take(1);
        assertEquals(depotClone.getOcc()-1,depot.getOcc());
    }
    /**
     * test method that tests for any exception that may occur in the action
     * of take resources from the extra depot if it is full
     */
    @Test
    void takeException() throws DepotOutOfBoundsException {
        depot.add(2);
        ExtraDepot depotClone = new ExtraDepot(depot.getOcc(), depot.getType());
        assertThrows(DepotOutOfBoundsException.class,()->depot.take(3));
    }

}