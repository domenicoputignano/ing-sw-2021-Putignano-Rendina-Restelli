package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.DepotNotFoundException;
import it.polimi.ingsw.Exceptions.DepotOutOfBoundsException;
import it.polimi.ingsw.Exceptions.PaymentErrorException;
import it.polimi.ingsw.Exceptions.StrongboxOutOfBoundException;
import it.polimi.ingsw.Utils.ResourceSource;

import java.util.EnumMap;
import java.util.Map;


public final class PaymentHandler {

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

    private static boolean checkResourcesFromNormalDepots(Warehouse warehouse, Map<ResourceType, Integer> resources) {
        for(ResourceType resourceType : resources.keySet()) {
            if(!warehouse.checkResourceFromNormalDepot(resourceType,resources.get(resourceType))) return false;
        }
        return true;
    }

    private static boolean checkResourcesFromStrongBox(Warehouse warehouse, Map<ResourceType, Integer> resources) {
        for(ResourceType resourceType : resources.keySet()) {
            if(!warehouse.checkResourceFromStrongBox(resourceType, resources.get(resourceType))) return false;
        }
        return true;
    }

    private static boolean checkResourcesFromExtraDepots(Warehouse warehouse, Map<ResourceType, Integer> resources) {
        for(ResourceType resourceType : resources.keySet()) {
            if(!warehouse.checkResourceFromExtraDepot(resourceType, resources.get(resourceType))) return false;
        }
        return true;
    }

    private static void takeResourcesFromNormalDepots(Warehouse warehouse, Map<ResourceType, Integer> resources) throws DepotNotFoundException, DepotOutOfBoundsException{
        for(ResourceType resourceType : resources.keySet()) {
            warehouse.takeResourceFromNormalDepot(resourceType,resources.get(resourceType));
        }
    }
    private static void takeResourcesFromExtraDepots(Warehouse warehouse, Map<ResourceType, Integer> resources) throws DepotOutOfBoundsException, DepotNotFoundException {
        for(ResourceType resourceType : resources.keySet()) {
            warehouse.takeResourceFromExtraDepot(resourceType, resources.get(resourceType));
        }
    }

    private static void takeResourcesFromStrongbox(Warehouse warehouse, Map<ResourceType, Integer> resources) throws StrongboxOutOfBoundException {
        warehouse.takeResourcesFromStrongbox(resources);
    }

    public static boolean checkCostCoherence(Map<ResourceSource,EnumMap<ResourceType,Integer>> howToTakeResources, Map<ResourceType,Integer> expectedCost)
    {
        return convertResource(howToTakeResources).equals(expectedCost);
    }



    public static boolean areValidInstructions(Map<ResourceSource, EnumMap<ResourceType, Integer>> howToTakeResources) {
        if(howToTakeResources.size() != 3 || howToTakeResources.keySet().stream().anyMatch(x -> howToTakeResources.get(x) == null) ||
                howToTakeResources.keySet().stream().anyMatch(x -> howToTakeResources.get(x).keySet().stream().anyMatch(y -> howToTakeResources.get(x).get(y) < 0)))
            return false;
        return true;
    }

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

