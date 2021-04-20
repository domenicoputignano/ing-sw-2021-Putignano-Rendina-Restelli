package it.polimi.ingsw.Utils;

import it.polimi.ingsw.Model.Card.CardType;
import it.polimi.ingsw.Model.ResourceType;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class BuyDevCardMessage {
    private CardType type;
    private Map<ResourceSource, EnumMap<ResourceType, Integer>> howToTakeResources = new HashMap<>();
    private int destinationSlot;


    public CardType getType() {
        return type;
    }

    public Map<ResourceSource, EnumMap<ResourceType, Integer>> getHowToTakeResources() {
        return howToTakeResources;
    }

    public int getDestinationSlot() {
        return destinationSlot;
    }

    //Da implementare in ciascuna classe messaggio
    public boolean isValidMessage()
    {
        if(type == null || type.getLevel() <=0 || type.getLevel() > 3 || type.getColor() == null)
            return false;
        if(destinationSlot <= 0 || destinationSlot > 3)
            return false;
        if(howToTakeResources.size() != 3 || howToTakeResources.keySet().stream().anyMatch(x -> howToTakeResources.get(x) == null) ||
                howToTakeResources.keySet().stream().anyMatch(x -> howToTakeResources.get(x).keySet().stream().anyMatch(y -> howToTakeResources.get(x).get(y) < 0)))
            return false;
        return true;
    }
}
