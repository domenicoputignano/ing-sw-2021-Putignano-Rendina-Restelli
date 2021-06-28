package it.polimi.ingsw.model;

import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.exceptions.DepotNotFoundException;
import it.polimi.ingsw.exceptions.DepotOutOfBoundsException;
import it.polimi.ingsw.exceptions.PaymentErrorException;
import it.polimi.ingsw.exceptions.StrongboxOutOfBoundException;
import it.polimi.ingsw.utils.ResourceSource;

import java.util.EnumMap;
import java.util.Map;

/**
 * This is a final class whose methods are used when a player has to pay something like a development card or a set of productions.
 */
public final class PaymentHandler {

    /**
     * Main method of the class. Starting from instructions sent by player, it checks whether they are compatible with
     * player's warehouse. If all checks are passed, the payment is performed as required.
     * @param warehouse player warehouse from which resources have to be taken.
     * @param howToTakeResources instructions sent by client side.
     * @param turn turn in which the player is trying to do the payment.
     * @throws PaymentErrorException if at least one check fails.
     */
    public static void performPayment(Warehouse warehouse, Map<ResourceSource, EnumMap<ResourceType, Integer>> howToTakeResources, Turn turn) throws DepotOutOfBoundsException, DepotNotFoundException, StrongboxOutOfBoundException, PaymentErrorException {
        EnumMap<ResourceType, Integer> toTakeFromNormalDepot = howToTakeResources.get(ResourceSource.DEPOT).clone();
        EnumMap<ResourceType, Integer> toTakeFromStrongBox = howToTakeResources.get(ResourceSource.STRONGBOX).clone();
        EnumMap<ResourceType, Integer> toTakeFromExtraDepot = howToTakeResources.get(ResourceSource.EXTRA).clone();

        boolean normalDepotsCheck = checkResourcesFromNormalDepots(warehouse,toTakeFromNormalDepot);
        boolean extraDepotsCheck = checkResourcesFromExtraDepots(warehouse,toTakeFromExtraDepot);
        boolean strongboxCheck = checkResourcesFromStrongBox(warehouse,toTakeFromStrongBox);
        if(normalDepotsCheck && extraDepotsCheck && strongboxCheck) {
                takeResourcesFromNormalDepots(warehouse,toTakeFromNormalDepot);
                takeResourcesFromExtraDepots(warehouse, toTakeFromExtraDepot);
                takeResourcesFromStrongbox(warehouse,toTakeFromStrongBox);
        } else {
            throw new PaymentErrorException();
        }
    }


    /**
     * This method ensures that for each resource type, there is a normal depot with the required occurrences.
     * @param warehouse warehouse involved into the check.
     * @param resources map that specifies for each resource type how many occurrences are required.
     * @return the result of the check
     * @see Warehouse
     */
    private static boolean checkResourcesFromNormalDepots(Warehouse warehouse, Map<ResourceType, Integer> resources) {
        for(ResourceType resourceType : resources.keySet()) {
            if(!warehouse.checkResourceFromNormalDepot(resourceType,resources.get(resourceType))) return false;
        }
        return true;
    }

    /**
     * This method ensures that for each resource type the strongbox has a number of occurrences
     * type compatible with what is required.
     * @param warehouse warehouse involved into the check.
     * @param resources map that specifies for each resource type how many occurrences are required.
     * @return the result of the check.
     * @see Warehouse
     */
    private static boolean checkResourcesFromStrongBox(Warehouse warehouse, Map<ResourceType, Integer> resources) {
        for(ResourceType resourceType : resources.keySet()) {
            if(!warehouse.checkResourceFromStrongBox(resourceType, resources.get(resourceType))) return false;
        }
        return true;
    }

    /**
     * This method ensures that for each resource type there is an extra depot with the required occurrences.
     * @param warehouse warehouse involved into the check.
     * @param resources map that specifies for each resource type how many occurrences are required.
     * @return the result of the check.
     * @see Warehouse
     */
    private static boolean checkResourcesFromExtraDepots(Warehouse warehouse, Map<ResourceType, Integer> resources) {
        for(ResourceType resourceType : resources.keySet()) {
            if(!warehouse.checkResourceFromExtraDepot(resourceType, resources.get(resourceType))) return false;
        }
        return true;
    }

    /**
     * For each resource type picks required occurrences from a normal depot, see {@link Warehouse} for further details.
     * @param warehouse player warehouse from which retrieve resources.
     * @param resources that have to be taken from normal depots.
     */
    private static void takeResourcesFromNormalDepots(Warehouse warehouse, Map<ResourceType, Integer> resources) throws DepotNotFoundException, DepotOutOfBoundsException{
        for(ResourceType resourceType : resources.keySet()) {
            warehouse.takeResourceFromNormalDepot(resourceType,resources.get(resourceType));
        }
    }

    /**
     * Takes resources from extra depots, see {@link Warehouse} for further details.
     * @param warehouse player warehouse from which retrieve resources.
     * @param resources that have to be taken from the extra depots.
     */
    private static void takeResourcesFromExtraDepots(Warehouse warehouse, Map<ResourceType, Integer> resources) throws DepotOutOfBoundsException, DepotNotFoundException {
        for(ResourceType resourceType : resources.keySet()) {
            warehouse.takeResourceFromExtraDepot(resourceType, resources.get(resourceType));
        }
    }

    /**
     * Takes resources from the strongbox
     * @param warehouse player warehouse from which retrieve resources.
     * @param resources that have to be taken from the strongbox.
     */
    private static void takeResourcesFromStrongbox(Warehouse warehouse, Map<ResourceType, Integer> resources) throws StrongboxOutOfBoundException {
        warehouse.takeResourcesFromStrongbox(resources);
    }

    /**
     * Boolean method that checks if there is matching between instructions given client side and the actual cost an action.
     */
    public static boolean checkCostCoherence(Map<ResourceSource,EnumMap<ResourceType,Integer>> howToTakeResources, Map<ResourceType,Integer> expectedCost)
    {
        return convertResource(howToTakeResources).equals(expectedCost);
    }


    /**
     * Boolean method that ensures that instructions given client side are correct.
     * @param howToTakeResources map of maps that establish from where and how much resources the player wants to take from
     *                           his normal depots, strongbox and extra depots.
     * @return the result of the check
     */
    public static boolean areValidInstructions(Map<ResourceSource, EnumMap<ResourceType, Integer>> howToTakeResources) {
        if(howToTakeResources.size() != 3 || howToTakeResources.keySet().stream().anyMatch(x -> howToTakeResources.get(x) == null) ||
                howToTakeResources.keySet().stream().anyMatch(x -> howToTakeResources.get(x).keySet().stream().anyMatch(y -> howToTakeResources.get(x).get(y) < 0)))
            return false;
        return true;
    }

    /**
     * Converts the input in a map that for each type of resource specifies the number of occurrences needed to perform an action.
     * @param howToTakeResources map of maps that establish from where and how much resources the player wants to take from
     *                           his normal depots, strongbox and extra depots.
     * @return resulting map
     */
    private static Map<ResourceType, Integer> convertResource(Map<ResourceSource,EnumMap<ResourceType,Integer>> howToTakeResources) {
        Map<ResourceType, Integer> resourcesToTake = new EnumMap<>(ResourceType.class);
        resourcesToTake.put(ResourceType.coin,0);
        resourcesToTake.put(ResourceType.servant,0);
        resourcesToTake.put(ResourceType.shield,0);
        resourcesToTake.put(ResourceType.stone,0);
        howToTakeResources.keySet().stream().forEach(x -> howToTakeResources.get(x).forEach((key, value) -> resourcesToTake.merge(key, value, Integer::sum)));
        return resourcesToTake;
    }
}

