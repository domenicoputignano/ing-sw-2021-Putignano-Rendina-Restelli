package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.DepotNotFoundException;
import it.polimi.ingsw.Exceptions.DepotOutOfBoundsException;
import it.polimi.ingsw.Exceptions.StrongboxOutOfBoundException;

import java.util.EnumMap;
import java.util.Map;


public final class PaymentHandler {
    public static void performPayment(Warehouse warehouse, Map<String, EnumMap<ResourceType, Integer>> howToTakeResources, Turn turn) {
        EnumMap<ResourceType, Integer> toTakeFromNormalDepot = howToTakeResources.get("Depot").clone();
        EnumMap<ResourceType, Integer> toTakeFromStrongBox = howToTakeResources.get("Strongbox").clone();
        EnumMap<ResourceType, Integer> toTakeFromExtraDepot = howToTakeResources.get("ExtraDepot").clone();

        boolean normalDepotsCheck = checkResourcesFromNormalDepots(warehouse,toTakeFromNormalDepot);
        boolean extraDepotsCheck = checkResourcesFromExtraDepots(warehouse,toTakeFromExtraDepot);
        boolean strongboxCheck = checkResourcesFromStrongBox(warehouse,toTakeFromStrongBox);
        if(normalDepotsCheck && extraDepotsCheck && strongboxCheck) {
            try {
                takeResourcesFromNormalDepots(warehouse,toTakeFromNormalDepot);
            } catch (DepotNotFoundException e) {
                e.printStackTrace();
            } catch (DepotOutOfBoundsException e) {
                e.printStackTrace();
            }
            try {
                takeResourcesFromExtraDepots(warehouse, toTakeFromExtraDepot);
            } catch (DepotOutOfBoundsException e) {
                e.printStackTrace();
            } catch (DepotNotFoundException e) {
                e.printStackTrace();
            }
            try {
                takeResourcesFromStrongbox(warehouse,toTakeFromStrongBox);
            } catch (StrongboxOutOfBoundException e) {
                e.printStackTrace();
            }
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


}

