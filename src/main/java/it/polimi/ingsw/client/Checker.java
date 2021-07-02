package it.polimi.ingsw.client;


import it.polimi.ingsw.client.reducedmodel.ReducedPersonalBoard;
import it.polimi.ingsw.commons.ResourceType;
import java.util.Map;

/**
 * This class provides methods to manage map widely used client side.
 */
public final class Checker {


    /**
     * Boolean method to check if owner of given player board instance has enough resources, comparing his available ones
     * with required ones.
     */
    public static boolean checkResources(Map<ResourceType, Integer> requiredResources, ReducedPersonalBoard playerBoard){
        Map<ResourceType, Integer> availableResources = playerBoard.getAvailableResources();
        return requiredResources.entrySet().stream().allMatch((entry) -> requiredResources.get(entry.getKey()) <= availableResources.get(entry.getKey()));
    }

    /**
     * Returns a map built through merging two input maps.
     */
    public static Map<ResourceType, Integer> mapMerging(Map<ResourceType, Integer> input1, Map<ResourceType, Integer> result) {
        input1.forEach((key,value) -> result.merge(key, value, Integer::sum));
        return result;
    }



}
