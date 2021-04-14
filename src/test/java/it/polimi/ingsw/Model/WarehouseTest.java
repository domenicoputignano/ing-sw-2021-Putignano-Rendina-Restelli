package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.DepotNotFoundException;
import it.polimi.ingsw.Exceptions.DepotOutOfBoundsException;
import it.polimi.ingsw.Exceptions.IncompatibleResourceTypeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.*;
import java.util.stream.Collectors;


import static org.junit.jupiter.api.Assertions.*;

class WarehouseTest {
    Warehouse warehouse = new Warehouse();
    Map<ResourceType,Integer> resource = new EnumMap<ResourceType, Integer>(ResourceType.class);
    Map<ResourceType,Integer> startingresource = new EnumMap<ResourceType, Integer>(ResourceType.class);


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



    @ParameterizedTest
    @ValueSource (ints = {0,1,2})
    void checkNormalDepotPositioning(int index) throws DepotOutOfBoundsException, IncompatibleResourceTypeException {
        warehouse.addResourcesToDepot(1, ResourceType.shield, 1);
        warehouse.addResourcesToDepot(2, ResourceType.servant, 2);
        warehouse.addResourcesToDepot(3,ResourceType.stone,3);
        if(index!= 1) assertFalse(warehouse.checkMoveFromNormalDepotToNormalDepot(index, 1));
        else assertTrue(warehouse.checkMoveFromNormalDepotToNormalDepot(index,1));
    }



    @Test
    void moveFromNormalDepotToNormalDepot() throws DepotOutOfBoundsException, IncompatibleResourceTypeException {
        warehouse.addResourcesToDepot(1, ResourceType.shield, 1);
        warehouse.addResourcesToDepot(2, ResourceType.servant, 2);
        warehouse.addResourcesToDepot(3,ResourceType.stone,2);
        List<NormalDepot> expected = new ArrayList<NormalDepot>();
        expected.add(new NormalDepot(1, ResourceType.shield,1));
        expected.add(new NormalDepot(2,ResourceType.stone,2));
        expected.add(new NormalDepot(2,ResourceType.servant,3));
        if(warehouse.checkMoveFromNormalDepotToNormalDepot(1,2)) {
            warehouse.moveFromNormalDepotToNormalDepot(1,2);
            assertEquals(Arrays.stream(warehouse.getNormalDepots()).collect(Collectors.toList()), expected);
        }
    }

    @Test
    void nullMoveFromNormalDepotToNormalDepot() throws DepotOutOfBoundsException, IncompatibleResourceTypeException {
        warehouse.addResourcesToDepot(1, ResourceType.shield, 1);
        warehouse.addResourcesToDepot(2, ResourceType.servant, 2);
        warehouse.moveFromNormalDepotToNormalDepot(1,2);
        for(int i = 0; i < 3; i++)
        System.out.println(warehouse.getNormalDepots()[i]);
    }


    @ParameterizedTest
    @EnumSource(ResourceType.class)
    void checkMoveFromNormalDepotToExtraDepot(ResourceType type) throws DepotOutOfBoundsException, IncompatibleResourceTypeException {
        //create an instance of an empty ExtraDepot
        warehouse.getExtraDepots()[0] = new ExtraDepot(type);
        warehouse.addResourcesToDepot(1,ResourceType.stone,1);
        if(type == ResourceType.stone)
            assertTrue(warehouse.checkMoveFromNormalDepotToExtraDepot(1,1,1));
        else
            assertFalse(warehouse.checkMoveFromNormalDepotToExtraDepot(1,1,1));
    }

    @ParameterizedTest
    @EnumSource(ResourceType.class)
    void checkMoveFromExtraDepotToNormalDepot(ResourceType type) throws DepotOutOfBoundsException, IncompatibleResourceTypeException, DepotNotFoundException {
        //create an instance of an empty ExtraDepot
        warehouse.getExtraDepots()[0] = new ExtraDepot(type);
        warehouse.addResourcesToExtraDepot(type, 2);
        warehouse.addResourcesToDepot(2, type, 1);
        assertFalse(warehouse.checkMoveFromExtraDepotToNormalDepot(2,2,1));
        assertThrows(IncompatibleResourceTypeException.class, () ->warehouse.checkMoveFromExtraDepotToNormalDepot(1,2,1));
        assertFalse(warehouse.checkMoveFromExtraDepotToNormalDepot(1,2,2));
        assertThrows(IncompatibleResourceTypeException.class, () ->warehouse.checkMoveFromExtraDepotToNormalDepot(1,2,3));
        assertThrows(IncompatibleResourceTypeException.class,()->warehouse.checkMoveFromExtraDepotToNormalDepot(1,1,1));
        assertTrue(warehouse.checkMoveFromExtraDepotToNormalDepot(1,1,2));

    }


    @ParameterizedTest
    @EnumSource(ResourceType.class)
    void addResourcesToExtraDepot(ResourceType type) throws DepotOutOfBoundsException, DepotNotFoundException {
        Random r = new Random();
        int occ  = r.nextInt(4);
        System.out.println(occ);
        warehouse.getExtraDepots()[0] = new ExtraDepot(ResourceType.coin);
        if(warehouse.getExtraDepots()[0].getType()!= type)
            assertThrows(DepotNotFoundException.class, ()->warehouse.addResourcesToExtraDepot(type,occ));
        else {
            if(warehouse.getExtraDepots()[0].getOcc() + occ > warehouse.getExtraDepots()[0].getSize())
                assertThrows(DepotOutOfBoundsException.class, () -> warehouse.addResourcesToExtraDepot(type,occ));
            else
                warehouse.addResourcesToExtraDepot(type,occ);
                assertTrue(warehouse.getExtraDepots()[0].getOcc() == occ);
        }
    }

    @Test
    void checkIsValidEditing() {

    }





    @Test
    void takeResources() {
        //TODO
    }

    @Test
    void updateAvailableResources() throws DepotOutOfBoundsException {
        //TODO
    }
}