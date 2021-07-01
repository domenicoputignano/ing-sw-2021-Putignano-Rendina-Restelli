package it.polimi.ingsw.model;

import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.exceptions.StrongboxOutOfBoundException;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class to test strongbox instance.
 */
public class StrongboxTest {
    Strongbox strongbox = new Strongbox();

    /**
     * Covers initialization
     */
    @Test
    void emptyInizialization()
    {
        assertTrue(strongbox.getResources().keySet().stream().allMatch((key) -> strongbox.getResources().get(key)==0));
    }

    /**
     * Perform addition to available resources in the strongbox and checks the result.
     */
    @Test
    void addResources() {
        EnumMap<ResourceType,Integer> resources = ((EnumMap<ResourceType, Integer>) strongbox.getResources()).clone();
        EnumMap<ResourceType,Integer> resourcesToAdd = new EnumMap<ResourceType, Integer>(ResourceType.class);
        resourcesToAdd.put(ResourceType.shield,1);
        resourcesToAdd.put(ResourceType.stone,2);
        resourcesToAdd.put(ResourceType.coin,0);
        resourcesToAdd.put(ResourceType.servant,0);
        strongbox.addResources(resourcesToAdd);
        assertTrue(strongbox.getResources().keySet().stream().allMatch((key) -> resources.get(key)+resourcesToAdd.get(key)==strongbox.getResources().get(key)));
    }

    /**
     * Checks map merging done while taking resources from strongbox.
     */
    @Test
    void takeResources() throws StrongboxOutOfBoundException {
        EnumMap<ResourceType,Integer> resourcesToAdd = new EnumMap<ResourceType, Integer>(ResourceType.class);
        resourcesToAdd.put(ResourceType.shield,2);
        resourcesToAdd.put(ResourceType.stone,3);
        resourcesToAdd.put(ResourceType.coin,4);
        resourcesToAdd.put(ResourceType.servant,1);
        strongbox.addResources(resourcesToAdd);
        EnumMap<ResourceType,Integer> resources = ((EnumMap<ResourceType, Integer>) strongbox.getResources()).clone();
        EnumMap<ResourceType,Integer> resourcesToTake = new EnumMap<>(ResourceType.class);
        resourcesToTake.put(ResourceType.shield,1);
        resourcesToTake.put(ResourceType.stone,2);
        resourcesToTake.put(ResourceType.coin,3);
        resourcesToTake.put(ResourceType.servant,0);
        strongbox.takeResources(resourcesToTake);
        assertTrue(strongbox.getResources().keySet().stream().allMatch((key) -> resources.get(key)-resourcesToTake.get(key)==strongbox.getResources().get(key)));
    }

    /**
     * Test of a failed attempt to take resources from strongbox.
     */
    @Test
    void takeResourcesException()
    {
        EnumMap<ResourceType,Integer> resourcesToAdd = new EnumMap<ResourceType, Integer>(ResourceType.class);
        resourcesToAdd.put(ResourceType.shield,2);
        resourcesToAdd.put(ResourceType.stone,3);
        resourcesToAdd.put(ResourceType.coin,4);
        resourcesToAdd.put(ResourceType.servant,1);
        strongbox.addResources(resourcesToAdd);
        EnumMap<ResourceType,Integer> resources = ((EnumMap<ResourceType, Integer>) strongbox.getResources()).clone();
        EnumMap<ResourceType,Integer> resourcesToTake = new EnumMap<ResourceType, Integer>(ResourceType.class);
        resourcesToTake.put(ResourceType.shield,2);
        resourcesToTake.put(ResourceType.stone,3);
        resourcesToTake.put(ResourceType.coin,5);
        resourcesToTake.put(ResourceType.servant,1);
        assertThrows(StrongboxOutOfBoundException.class,()->strongbox.takeResources(resourcesToTake));
    }
}