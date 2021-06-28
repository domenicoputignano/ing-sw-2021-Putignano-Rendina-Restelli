package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.ActiveProductions;
import it.polimi.ingsw.commons.ResourceType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import it.polimi.ingsw.utils.messages.clientMessages.*;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

class ActivateProductionMessageTest {

    ActivateProductionMessage activateProductionMessage = new ActivateProductionMessage();
    ActiveProductions activeProductions = new ActiveProductions();
    Map<ResourceSource, EnumMap<ResourceType, Integer>> howToTakeResources = new HashMap<>();
    EnumMap<ResourceType,Integer> Strongbox = new EnumMap<ResourceType, Integer>(ResourceType.class);
    EnumMap<ResourceType,Integer> Depot = new EnumMap<ResourceType, Integer>(ResourceType.class);
    EnumMap<ResourceType,Integer> ExtraDepot = new EnumMap<ResourceType, Integer>(ResourceType.class);

    @Test
    void correct() {
        activeProductions.setBasic(true);
        activeProductions.setSlot1(true);
        activeProductions.setSlot2(true);
        activeProductions.setSlot3(true);
        activateProductionMessage.setProductions(activeProductions);

        howToTakeResources.put(ResourceSource.STRONGBOX, Strongbox);
        howToTakeResources.put(ResourceSource.DEPOT, Depot);
        howToTakeResources.put(ResourceSource.EXTRA, ExtraDepot);

        activateProductionMessage.setInput1(ResourceType.coin);
        activateProductionMessage.setInput2(ResourceType.servant);
        activateProductionMessage.setOutput(ResourceType.shield);
        activateProductionMessage.setHowToTakeResources(howToTakeResources);

        assertTrue(activateProductionMessage.isValidMessage());
    }

    @Test
    void falseBecauseOfBasic() {
        activeProductions.setBasic(true);
        activateProductionMessage.setProductions(activeProductions);
        System.out.println(activateProductionMessage.getProductions());
        assertFalse(activateProductionMessage.isValidMessage());
    }

     @Test
    void falseBecauseOfNonCompleteMap() {
        activeProductions.setSlot1(true);
        activateProductionMessage.setProductions(activeProductions);
        System.out.println(activateProductionMessage.getProductions());
        System.out.println(activeProductions);
        howToTakeResources.put(ResourceSource.STRONGBOX, Strongbox);
        howToTakeResources.put(ResourceSource.EXTRA, ExtraDepot);
        activateProductionMessage.setHowToTakeResources(howToTakeResources);
        assertFalse(activateProductionMessage.isValidMessage());
     }

    @Test
    void falseBecauseOfNoneSlotHasBeenSelected() {
        activateProductionMessage.setProductions(activeProductions);
        System.out.println(activateProductionMessage.getProductions());
        System.out.println(activeProductions);
        howToTakeResources.put(ResourceSource.STRONGBOX, Strongbox);
        howToTakeResources.put(ResourceSource.DEPOT, Depot);
        howToTakeResources.put(ResourceSource.EXTRA, ExtraDepot);
        activateProductionMessage.setHowToTakeResources(howToTakeResources);
        assertFalse(activateProductionMessage.isValidMessage());
    }

    @Test
    void falseBecauseOfNullInMapEntrySet() {
        activeProductions.setExtraSlot1(true);
        howToTakeResources.put(ResourceSource.STRONGBOX, null);
        howToTakeResources.put(ResourceSource.DEPOT, Depot);
        howToTakeResources.put(ResourceSource.EXTRA, ExtraDepot);
        activateProductionMessage.setProductions(activeProductions);
        assertFalse(activateProductionMessage.isValidMessage());
    }


    @Test
    void falseBecauseOfExtra() {
        activeProductions.setExtraSlot1(true);
        activeProductions.setExtraSlot2(true);
        activateProductionMessage.setProductions(activeProductions);
        activateProductionMessage.setOutputExtra1(ResourceType.servant);
        howToTakeResources.put(ResourceSource.STRONGBOX, Strongbox);
        howToTakeResources.put(ResourceSource.DEPOT, Depot);
        howToTakeResources.put(ResourceSource.EXTRA, ExtraDepot);
        activateProductionMessage.setHowToTakeResources(howToTakeResources);
        assertFalse(activateProductionMessage.isValidMessage());
    }

    @Test
    void trueWithOfExtra() {
        activeProductions.setExtraSlot1(true);
        activeProductions.setExtraSlot2(true);
        activateProductionMessage.setProductions(activeProductions);
        activateProductionMessage.setOutputExtra1(ResourceType.servant);
        activateProductionMessage.setOutputExtra2(ResourceType.shield);
        howToTakeResources.put(ResourceSource.STRONGBOX, Strongbox);
        howToTakeResources.put(ResourceSource.DEPOT, Depot);
        howToTakeResources.put(ResourceSource.EXTRA, ExtraDepot);
        activateProductionMessage.setHowToTakeResources(howToTakeResources);
        assertTrue(activateProductionMessage.isValidMessage());
    }


}
