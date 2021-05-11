package it.polimi.ingsw.Client;


import it.polimi.ingsw.Commons.ResourceType;
import java.util.Map;

public final class Checker {


    public static boolean checkResources(Map<ResourceType, Integer> requiredResources, ReducedPersonalBoard playerBoard){
        Map<ResourceType, Integer> availableResources = playerBoard.getAvailableResources();
        return requiredResources.entrySet().stream().allMatch((entry) -> requiredResources.get(entry.getKey()) <= availableResources.get(entry.getKey()));
    }

    public static Map<ResourceType, Integer> mapMerging(Map<ResourceType, Integer> input1, Map<ResourceType, Integer> result) {
        input1.forEach((key,value) -> result.merge(key, value, Integer::sum));
        return result;
    }



}
