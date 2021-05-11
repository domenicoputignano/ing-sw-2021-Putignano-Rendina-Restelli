package it.polimi.ingsw.Client;


import it.polimi.ingsw.Commons.Effect;
import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Model.ActiveProductions;
import it.polimi.ingsw.Utils.Messages.ClientMessages.ActivateProductionMessage;

import java.util.EnumMap;
import java.util.Map;

public final class Checker {



    public static boolean checkRequiredResources(ActivateProductionMessage message, ReducedPersonalBoard playerBoard){
        Map<ResourceType, Integer> neededResources = calculateInputResources(message, playerBoard);
        Map<ResourceType, Integer> availableResources = playerBoard.getAvailableResources();
        return neededResources.entrySet().stream().allMatch((entry) -> neededResources.get(entry.getKey()) <= availableResources.get(entry.getKey()));
    }

    private static Map<ResourceType, Integer> calculateInputResources(ActivateProductionMessage message, ReducedPersonalBoard playerBoard) {
        EnumMap<ResourceType, Integer> result = new EnumMap<ResourceType, Integer>(ResourceType.class);
        result.put(ResourceType.coin,0);
        result.put(ResourceType.servant,0);
        result.put(ResourceType.shield,0);
        result.put(ResourceType.stone,0);
        if(message.getProductions().isSlot1()) mapMerging(retrieveCardInputResources(playerBoard,0), result);
        if(message.getProductions().isSlot2()) mapMerging(retrieveCardInputResources(playerBoard, 1), result);
        if(message.getProductions().isSlot3()) mapMerging(retrieveCardInputResources(playerBoard,2), result);
        if(message.getProductions().isExtraSlot1()) {
            ResourceType type = retrieveInputExtra(playerBoard, 1);
            result.put(type, result.get(type)+1);
        }
        if(message.getProductions().isExtraSlot2()) {
            ResourceType type = retrieveInputExtra(playerBoard, 2);
            result.put(type, result.get(type)+1);
        }
        return result;
    }


    private static Map<ResourceType, Integer> mapMerging(Map<ResourceType, Integer> input1, Map<ResourceType, Integer> result) {
        input1.forEach((key,value) -> result.merge(key, value, Integer::sum));
        return result;
    }


    private static Map<ResourceType, Integer> retrieveCardInputResources(ReducedPersonalBoard playerBoard, int slotIndex) {
        return playerBoard.peekTopCardInSlot(slotIndex).getTrade().getInputResources();
    }

    //method called when we have an ExtraProduction effect, to find which ResourceType has to be transformed
    private static ResourceType retrieveInputExtra(ReducedPersonalBoard playerBoard, int extraSlot) {
        return playerBoard.findEffect(Effect.EXTRAPRODUCTION).get(extraSlot-1).getLeaderEffect().getType();
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
