package it.polimi.ingsw.utils;

import it.polimi.ingsw.commons.CardType;
import it.polimi.ingsw.commons.ColorCard;
import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.utils.messages.clientMessages.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Test class whose task is to check all possible outcomes for {@link BuyDevCardMessage} validity.
 */
class BuyDevCardMessageTest {
    BuyDevCardMessage buyDevCardMessage = new BuyDevCardMessage();


    @Test
    void correct() {
        buyDevCardMessage.setType(new CardType(1, ColorCard.green));
        Map<ResourceSource, EnumMap<ResourceType, Integer>> howToTakeResources = new HashMap<>();
        EnumMap<ResourceType,Integer> strongBox = new EnumMap<ResourceType, Integer>(ResourceType.class);
        strongBox.put(ResourceType.shield, 2);
        EnumMap<ResourceType,Integer> depot = new EnumMap<ResourceType, Integer>(ResourceType.class);
        EnumMap<ResourceType,Integer> extraDepot = new EnumMap<ResourceType, Integer>(ResourceType.class);
        howToTakeResources.put(ResourceSource.STRONGBOX, strongBox);
        howToTakeResources.put(ResourceSource.DEPOT, depot);
        howToTakeResources.put(ResourceSource.EXTRA, extraDepot);
        buyDevCardMessage.setHowToTakeResources(howToTakeResources);
        buyDevCardMessage.setDestinationSlot(1);
        assertTrue(buyDevCardMessage.isValidMessage());
    }

    @Test
    void falseBecauseOfMapOfMap() {
        buyDevCardMessage.setType(new CardType(1, ColorCard.green));
        Map<ResourceSource, EnumMap<ResourceType, Integer>> howToTakeResources = new HashMap<>();
        EnumMap<ResourceType,Integer> strongBox = new EnumMap<ResourceType, Integer>(ResourceType.class);
        strongBox.put(ResourceType.shield, 2);
        EnumMap<ResourceType,Integer> depot = new EnumMap<ResourceType, Integer>(ResourceType.class);
        howToTakeResources.put(ResourceSource.STRONGBOX, strongBox);
        howToTakeResources.put(ResourceSource.DEPOT, depot);
        howToTakeResources.put(ResourceSource.EXTRA, null);
        buyDevCardMessage.setHowToTakeResources(howToTakeResources);
        buyDevCardMessage.setDestinationSlot(1);
        assertFalse(buyDevCardMessage.isValidMessage());
    }

    @Test
    void falseBecauseOfIndex() {
        buyDevCardMessage.setType(new CardType(1, ColorCard.green));
        Map<ResourceSource, EnumMap<ResourceType, Integer>> howToTakeResources = new HashMap<>();
        EnumMap<ResourceType,Integer> strongBox = new EnumMap<ResourceType, Integer>(ResourceType.class);
        strongBox.put(ResourceType.shield, 2);
        EnumMap<ResourceType,Integer> depot = new EnumMap<ResourceType, Integer>(ResourceType.class);
        EnumMap<ResourceType,Integer> extraDepot = new EnumMap<ResourceType, Integer>(ResourceType.class);
        howToTakeResources.put(ResourceSource.STRONGBOX, strongBox);
        howToTakeResources.put(ResourceSource.DEPOT, depot);
        howToTakeResources.put(ResourceSource.EXTRA, extraDepot);
        buyDevCardMessage.setHowToTakeResources(howToTakeResources);
        buyDevCardMessage.setDestinationSlot(4);
        assertFalse(buyDevCardMessage.isValidMessage());
    }

    @Test
    void falseBecauseOfCardType() {
        buyDevCardMessage.setType(new CardType(4, ColorCard.blue));
        Map<ResourceSource, EnumMap<ResourceType, Integer>> howToTakeResources = new HashMap<>();
        EnumMap<ResourceType,Integer> strongBox = new EnumMap<ResourceType, Integer>(ResourceType.class);
        strongBox.put(ResourceType.shield, 2);
        EnumMap<ResourceType,Integer> depot = new EnumMap<ResourceType, Integer>(ResourceType.class);
        EnumMap<ResourceType,Integer> extraDepot = new EnumMap<ResourceType, Integer>(ResourceType.class);
        howToTakeResources.put(ResourceSource.STRONGBOX, strongBox);
        howToTakeResources.put(ResourceSource.DEPOT, depot);
        howToTakeResources.put(ResourceSource.EXTRA, extraDepot);
        buyDevCardMessage.setHowToTakeResources(howToTakeResources);
        buyDevCardMessage.setDestinationSlot(1);
        assertFalse(buyDevCardMessage.isValidMessage());
    }

    @Test
    void falseBecauseOfCardType2() {
        buyDevCardMessage.setType(new CardType(1, null));
        Map<ResourceSource, EnumMap<ResourceType, Integer>> howToTakeResources = new HashMap<>();
        EnumMap<ResourceType,Integer> strongBox = new EnumMap<ResourceType, Integer>(ResourceType.class);
        strongBox.put(ResourceType.shield, 2);
        EnumMap<ResourceType,Integer> depot = new EnumMap<ResourceType, Integer>(ResourceType.class);
        EnumMap<ResourceType,Integer> extraDepot = new EnumMap<ResourceType, Integer>(ResourceType.class);
        howToTakeResources.put(ResourceSource.STRONGBOX, strongBox);
        howToTakeResources.put(ResourceSource.DEPOT, depot);
        howToTakeResources.put(ResourceSource.EXTRA, extraDepot);
        buyDevCardMessage.setHowToTakeResources(howToTakeResources);
        buyDevCardMessage.setDestinationSlot(1);
        assertFalse(buyDevCardMessage.isValidMessage());
    }


}