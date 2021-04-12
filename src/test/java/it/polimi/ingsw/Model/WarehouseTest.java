package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.DepotNotFoundException;
import it.polimi.ingsw.Exceptions.DepotOutOfBoundsException;
import it.polimi.ingsw.Exceptions.IncompatibleResourceTypeException;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
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
        if(index!= 1) assertFalse(warehouse.checkPositioningNormalDepots(index, 1));
        else assertTrue(warehouse.checkPositioningNormalDepots(index,1));
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
        if(warehouse.checkPositioningNormalDepots(1,2)) {
            warehouse.moveFromNormalDepotToNormalDepot(1,2);
            for(int i = 0; i < 3; i++)
            System.out.println(warehouse.getNormalDepots()[i]);
            assertEquals(Arrays.stream(warehouse.getNormalDepots()).collect(Collectors.toList()), expected);
        }
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