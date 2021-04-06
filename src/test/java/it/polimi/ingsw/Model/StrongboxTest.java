package it.polimi.ingsw.Model;

import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;
import java.util.EnumMap;

import static org.junit.jupiter.api.Assertions.*;

public class StrongboxTest {
    Strongbox strongbox = new Strongbox();

    @Test
    void emptyInizialization()
    {
        assertTrue(strongbox.getResources().keySet().stream().allMatch((key) -> strongbox.getResources().get(key)==0));
    }
    @Test
    void addResources() {
        EnumMap<ResourceType,Integer> resources = strongbox.getResources().clone();
        EnumMap<ResourceType,Integer> resourcesToAdd = new EnumMap<ResourceType, Integer>(ResourceType.class);
        resourcesToAdd.put(ResourceType.shield,1);
        resourcesToAdd.put(ResourceType.stone,2);
        resourcesToAdd.put(ResourceType.coin,0);
        resourcesToAdd.put(ResourceType.servant,0);
        strongbox.addResources(resourcesToAdd);
        assertTrue(strongbox.getResources().keySet().stream().allMatch((key) -> resources.get(key)+resourcesToAdd.get(key)==strongbox.getResources().get(key)));
    }
    @Test
    void takeResources()
    {
        EnumMap<ResourceType,Integer> resourcesToAdd = new EnumMap<ResourceType, Integer>(ResourceType.class);
        resourcesToAdd.put(ResourceType.shield,2);
        resourcesToAdd.put(ResourceType.stone,3);
        resourcesToAdd.put(ResourceType.coin,4);
        resourcesToAdd.put(ResourceType.servant,1);
        strongbox.addResources(resourcesToAdd);
        EnumMap<ResourceType,Integer> resources = strongbox.getResources().clone();
        EnumMap<ResourceType,Integer> resourcesToTake = new EnumMap<ResourceType, Integer>(ResourceType.class);
        resourcesToTake.put(ResourceType.shield,1);
        resourcesToTake.put(ResourceType.stone,2);
        resourcesToTake.put(ResourceType.coin,3);
        resourcesToTake.put(ResourceType.servant,0);
        strongbox.takeResources(resourcesToTake);
        assertTrue(strongbox.getResources().keySet().stream().allMatch((key) -> resources.get(key)-resourcesToTake.get(key)==strongbox.getResources().get(key)));
    }
    @Test
    void takeResourcesException()
    {
        EnumMap<ResourceType,Integer> resourcesToAdd = new EnumMap<ResourceType, Integer>(ResourceType.class);
        resourcesToAdd.put(ResourceType.shield,2);
        resourcesToAdd.put(ResourceType.stone,3);
        resourcesToAdd.put(ResourceType.coin,4);
        resourcesToAdd.put(ResourceType.servant,1);
        strongbox.addResources(resourcesToAdd);
        EnumMap<ResourceType,Integer> resources = strongbox.getResources().clone();
        EnumMap<ResourceType,Integer> resourcesToTake = new EnumMap<ResourceType, Integer>(ResourceType.class);
        resourcesToTake.put(ResourceType.shield,2);
        resourcesToTake.put(ResourceType.stone,3);
        resourcesToTake.put(ResourceType.coin,5);
        resourcesToTake.put(ResourceType.servant,1);
        assertThrows(InvalidParameterException.class,()->strongbox.takeResources(resourcesToTake));
    }
}