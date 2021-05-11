package it.polimi.ingsw.Client;


import it.polimi.ingsw.Commons.Effect;
import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Model.ActiveProductions;
import it.polimi.ingsw.Utils.Messages.ClientMessages.ActivateProductionMessage;

import java.util.EnumMap;
import java.util.Map;

public final class Checker {

    public static boolean checkRequiredResources(Map<ResourceType,Integer> availableResources){
        return false;
    }

    public Map<ResourceType, Integer> calculateInputResources(ActivateProductionMessage message, ReducedPersonalBoard playerBoard) {
        EnumMap<ResourceType, Integer> result = new EnumMap<ResourceType, Integer>(ResourceType.class);
        result.put(ResourceType.coin,0);
        result.put(ResourceType.servant,0);
        result.put(ResourceType.shield,0);
        result.put(ResourceType.stone,0);

        return result;
    }


    private Map<ResourceType, Integer> retrieveCardInputResources(ReducedPersonalBoard playerBoard, int slotIndex) {
        return playerBoard.peekTopCardInSlot(slotIndex).getTrade().getInputResources();
    }


    public static boolean areValidRequestedProductions(ReducedPersonalBoard playerBoard, ActiveProductions requiredProductions){
        if(requiredProductions.isSlot1()&& isEmptySlot(playerBoard,0)) return false;
        if(requiredProductions.isSlot2() && isEmptySlot(playerBoard, 1)) return false;
        if(requiredProductions.isSlot3() && isEmptySlot(playerBoard, 2)) return false;
        if(requiredProductions.isExtraSlot1() && playerBoard.findEffect(Effect.EXTRAPRODUCTION).size() < 1) return false;
        if(requiredProductions.isExtraSlot2() && playerBoard.findEffect(Effect.EXTRAPRODUCTION).size() < 2) return false;
        return true;
    }



    private static boolean isEmptySlot(ReducedPersonalBoard playerBoard, int slotIndex) {
        return playerBoard.isEmptySlot(slotIndex);
    }



}
