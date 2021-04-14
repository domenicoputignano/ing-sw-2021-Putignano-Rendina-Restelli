package it.polimi.ingsw.Utils;

import it.polimi.ingsw.Model.Card.CardType;
import it.polimi.ingsw.Model.ResourceType;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class BuyDevCardMessage {
    private CardType type;
    private Map<String, EnumMap<ResourceType, Integer>> howToTakeResources = new HashMap<>();
    private int destionationSlot;


    public CardType getType() {
        return type;
    }

    public Map<String, EnumMap<ResourceType, Integer>> getHowToTakeResources() {
        return howToTakeResources;
    }

    public int getDestionationSlot() {
        return destionationSlot;
    }
}
