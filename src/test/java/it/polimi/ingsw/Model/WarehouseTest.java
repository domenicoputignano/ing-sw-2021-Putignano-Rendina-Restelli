package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.DepotNotFoundException;
import it.polimi.ingsw.Exceptions.DepotOutOfBoundsException;
import it.polimi.ingsw.Exceptions.IncompatibleResourceTypeException;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.Map;

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



    @Test
    void takeResources() {
    }

    @Test
    void updateAvailableResources() throws DepotOutOfBoundsException {

    }
}