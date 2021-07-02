package it.polimi.ingsw.model;

import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.exceptions.DepotNotFoundException;
import it.polimi.ingsw.exceptions.DepotOutOfBoundsException;
import it.polimi.ingsw.exceptions.IncompatibleResourceTypeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.*;


import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class that tests the correct functioning of the Warehouse class and its functionalities.
 */
class WarehouseTest {
    Warehouse warehouse = new Warehouse();
    Map<ResourceType,Integer> resource = new EnumMap<>(ResourceType.class);
    Map<ResourceType,Integer> startingresource = new EnumMap<>(ResourceType.class);


    /**
     * Test method that tests the correct functionality of the method to check that a player has enough resources in order
     * to perform an action.
     */
    @Test
    void checkResources() throws DepotOutOfBoundsException, IncompatibleResourceTypeException {
        resource.put(ResourceType.coin, 3);
        resource.put(ResourceType.shield, 2);
        resource.put(ResourceType.servant, 2);
        resource.put(ResourceType.stone, 2);

        startingresource.put(ResourceType.servant,2);
        startingresource.put(ResourceType.coin,3);

        warehouse.addResourcesToStrongbox(startingresource);

        warehouse.addResourcesToDepot(1,ResourceType.servant,1);
        warehouse.addResourcesToDepot(2,ResourceType.shield,2);
        warehouse.addResourcesToDepot(3,ResourceType.stone,2);

        assertTrue(warehouse.checkResources(resource));

    }

    /**
     * Test method that tests the correct behaviour of the checkNormalDepotPositioning method of the related class.
     * It is executed 3 times with different parameters in order to have some form of randomness.
     * @param index the nummber of resources to position
     */
    @ParameterizedTest
    @ValueSource (ints = {1,2,3})
    void checkNormalDepotPositioning(int index) throws DepotOutOfBoundsException, IncompatibleResourceTypeException {
        warehouse.addResourcesToDepot(1, ResourceType.shield, 1);
        warehouse.addResourcesToDepot(2, ResourceType.servant, 2);
        warehouse.addResourcesToDepot(3,ResourceType.stone,3);
        if(index!= 1) assertFalse(warehouse.checkMoveFromNormalDepotToNormalDepot(index, 1));
        else assertTrue(warehouse.checkMoveFromNormalDepotToNormalDepot(index,1));
    }

    /**
     * Test method that tests the correct behaviour of the method used to move resources from an extra depot
     * to a normal depot.
     */
    @Test
    void moveFromExtraDepotToNormalDepot() throws DepotOutOfBoundsException, IncompatibleResourceTypeException, DepotNotFoundException {
        warehouse.addResourcesToDepot(1, ResourceType.shield, 1);
        //warehouse.addResourcesToDepot(2, ResourceType.servant, 0);
        warehouse.addResourcesToDepot(3, ResourceType.stone, 2);
        warehouse.initializeExtraDepot(ResourceType.servant);
        warehouse.initializeExtraDepot(ResourceType.stone);
        warehouse.addResourcesToExtraDepot(ResourceType.servant, 2);
        warehouse.addResourcesToExtraDepot(ResourceType.stone, 1);
        List<NormalDepot> expected = new ArrayList<NormalDepot>();
        List<ExtraDepot> expectedExtra = new ArrayList<ExtraDepot>();
        expected.add(new NormalDepot(1, ResourceType.shield, 1));
        expected.add(new NormalDepot(2, ResourceType.servant, 2));
        expected.add(new NormalDepot(3, ResourceType.stone, 3));
        expectedExtra.add(new ExtraDepot(0, ResourceType.servant));
        expectedExtra.add(new ExtraDepot(0, ResourceType.stone));
        if (warehouse.checkMoveFromExtraDepotToNormalDepot(1, 2, 2)
                && warehouse.checkMoveFromExtraDepotToNormalDepot(2, 1, 3)) {
            warehouse.moveFromExtraDepotToNormalDepot(1, 2, 2);
            warehouse.moveFromExtraDepotToNormalDepot(2, 1, 3);
            assertEquals(warehouse.getNormalDepots(), expected);
            assertEquals(warehouse.getExtraDepots(), expectedExtra);
        }
    }

    /**
     * Test method that tests the correct behaviour of the method used to move resources from a normal depot
     * to an extra depot.
     */
    @Test
    void moveFromNormalDepotToExtraDepot() throws DepotOutOfBoundsException, IncompatibleResourceTypeException {
        warehouse.addResourcesToDepot(1, ResourceType.shield, 1);
        warehouse.addResourcesToDepot(2, ResourceType.servant, 2);
        warehouse.addResourcesToDepot(3,ResourceType.stone,2);
        warehouse.initializeExtraDepot(ResourceType.servant);
        warehouse.initializeExtraDepot(ResourceType.stone);
        List<NormalDepot> expected = new ArrayList<NormalDepot>();
        List<ExtraDepot> expectedExtra = new ArrayList<ExtraDepot>();
        expected.add(new NormalDepot(1, ResourceType.shield,1));
        expected.add(new NormalDepot(0,null ,2));
        expected.add(new NormalDepot(1,ResourceType.stone,3));
        expectedExtra.add(new ExtraDepot(2,ResourceType.servant));
        expectedExtra.add(new ExtraDepot(1,ResourceType.stone));
        if(warehouse.checkMoveFromNormalDepotToExtraDepot(2,2,1)
        && warehouse.checkMoveFromNormalDepotToExtraDepot(3,1,2)) {
            warehouse.moveFromNormalDepotToExtraDepot(2,2,1);
            warehouse.moveFromNormalDepotToExtraDepot(3,1,2);
            assertEquals(warehouse.getNormalDepots(), expected);
            assertEquals(warehouse.getExtraDepots(),expectedExtra);
        }
    }

    /**
     * Test method that tests the correct behaviour of the method used to move resources from a normal depot
     * to a normal depot.
     */
    @Test
    void moveFromNormalDepotToNormalDepot() throws DepotOutOfBoundsException, IncompatibleResourceTypeException {
        warehouse.addResourcesToDepot(1, ResourceType.shield, 1);
        warehouse.addResourcesToDepot(2, ResourceType.servant, 2);
        warehouse.addResourcesToDepot(3,ResourceType.stone,2);
        List<NormalDepot> expected = new ArrayList<NormalDepot>();
        expected.add(new NormalDepot(1, ResourceType.shield,1));
        expected.add(new NormalDepot(2,ResourceType.stone,2));
        expected.add(new NormalDepot(2,ResourceType.servant,3));
        if(warehouse.checkMoveFromNormalDepotToNormalDepot(2,3)) {
            warehouse.moveFromNormalDepotToNormalDepot(2,3);
            assertEquals(warehouse.getNormalDepots(), expected);
        }
    }

    @Test
    void nullMoveFromNormalDepotToNormalDepot() throws DepotOutOfBoundsException, IncompatibleResourceTypeException {
        warehouse.addResourcesToDepot(1, ResourceType.shield, 1);
        warehouse.addResourcesToDepot(2, ResourceType.servant, 2);
        warehouse.moveFromNormalDepotToNormalDepot(1,2);
        for(int i = 0; i < 3; i++)
        System.out.println(warehouse.getNormalDepots().get(i));
    }

    /**
     * Method to test the correct behaviour of the method to check if a move from a normal depot to an extra depot
     * can be performed. It is executed 3 times with different parameters in order to have some form of randomness.
     * @param type the resource type to associate with the extra depot to create.
     */
    @ParameterizedTest
    @EnumSource(ResourceType.class)
    void checkMoveFromNormalDepotToExtraDepot(ResourceType type) throws DepotOutOfBoundsException, IncompatibleResourceTypeException {
        //create an instance of an empty ExtraDepot
        warehouse.initializeExtraDepot(type);
        warehouse.addResourcesToDepot(1,ResourceType.stone,1);
        if(type == ResourceType.stone)
            assertTrue(warehouse.checkMoveFromNormalDepotToExtraDepot(1,1,1));
        else
            assertFalse(warehouse.checkMoveFromNormalDepotToExtraDepot(1,1,1));
    }

    /**
     * Method to test the correct behaviour of the method to check if a move from an extra depot to a normal depot
     * can be performed. It is executed 3 times with different parameters in order to have some form of randomness.
     * @param type the resource type to associate with the extra depot to create.
     */
    @ParameterizedTest
    @EnumSource(ResourceType.class)
    void checkMoveFromExtraDepotToNormalDepot(ResourceType type) throws DepotOutOfBoundsException, IncompatibleResourceTypeException, DepotNotFoundException {
        //create an instance of an empty ExtraDepot
        warehouse.initializeExtraDepot(type);
        warehouse.addResourcesToExtraDepot(type, 2);
        warehouse.addResourcesToDepot(2, type, 1);
        assertFalse(warehouse.checkMoveFromExtraDepotToNormalDepot(2,2,1));
        assertFalse(warehouse.checkMoveFromExtraDepotToNormalDepot(1,2,1));
        assertFalse(warehouse.checkMoveFromExtraDepotToNormalDepot(1,2,2));
        assertFalse(warehouse.checkMoveFromExtraDepotToNormalDepot(1,2,3));
        assertFalse(warehouse.checkMoveFromExtraDepotToNormalDepot(1,1,1));
        assertTrue(warehouse.checkMoveFromExtraDepotToNormalDepot(1,1,2));

    }

    /**
     * Method to test the correct behaviour of the method used to take resources from a normal depot.
     */
    @Test
    void takeResourceFromNormalDepot() throws DepotOutOfBoundsException, IncompatibleResourceTypeException, DepotNotFoundException {
        warehouse.addResourcesToDepot(3, ResourceType.shield, 3);
        warehouse.takeResourceFromNormalDepot(ResourceType.shield,2);
        List<NormalDepot> expected = new ArrayList<>();
        expected.add(new NormalDepot(0, null,1));
        expected.add(new NormalDepot(0, null,2));
        expected.add(new NormalDepot(1, ResourceType.shield,3));
        assertEquals(warehouse.getNormalDepots(),expected);
    }

    /**
     * Method to test the correct behaviour of the method to add resources to an extra depot.
     * It is executed 3 times with different parameters in order to have some form of randomness.
     * @param type the resource type to associate with the extra depot to create.
     */
    @ParameterizedTest
    @EnumSource(ResourceType.class)
    void addResourcesToExtraDepot(ResourceType type) throws DepotOutOfBoundsException, DepotNotFoundException {
        Random r = new Random();
        int occ  = r.nextInt(3);
        System.out.println(occ);
        warehouse.initializeExtraDepot(ResourceType.coin);
        if(warehouse.getExtraDepots().get(0).getType()!= type)
            assertThrows(DepotNotFoundException.class, ()->warehouse.addResourcesToExtraDepot(type,occ));
        else {
            if(warehouse.getExtraDepots().get(0).getOcc() + occ > warehouse.getExtraDepots().get(0).getSize())
                assertThrows(DepotOutOfBoundsException.class, () -> warehouse.addResourcesToExtraDepot(type,occ));
            else
                warehouse.addResourcesToExtraDepot(type,occ);
                assertTrue(warehouse.getExtraDepots().get(0).getOcc() == occ);
        }
    }

    /**
     * Method to test the correct computation of the victory points gained from the warehouse.
     */
    @Test
    void calcVictoryPointsWarehouse() throws DepotOutOfBoundsException, IncompatibleResourceTypeException {
        warehouse.addResourcesToDepot(3, ResourceType.shield, 3);
        warehouse.addResourcesToDepot(2, ResourceType.coin, 2);
        warehouse.addResourcesToDepot(1, ResourceType.stone, 1);
        startingresource.put(ResourceType.servant,2);
        startingresource.put(ResourceType.coin,3);
        startingresource.put(ResourceType.stone,4);
        startingresource.put(ResourceType.shield,1);
        warehouse.addResourcesToStrongbox(startingresource);
        int victoryPoints = warehouse.calcVictoryPointsWarehouse();
        assertEquals(3,victoryPoints);
    }
}