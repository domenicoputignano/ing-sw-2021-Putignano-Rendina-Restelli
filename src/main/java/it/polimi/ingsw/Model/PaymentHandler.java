package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.DepotNotFoundException;
import it.polimi.ingsw.Exceptions.DepotOutOfBoundsException;
import it.polimi.ingsw.Exceptions.StrongboxOutOfBoundException;

import java.util.Map;


public interface PaymentHandler {



    default boolean checkResourcesFromNormalDepots(Warehouse warehouse, Map<ResourceType,Integer> resources) {
        for(ResourceType resourceType : resources.keySet()) {
            if(!warehouse.checkResourceFromNormalDepot(resourceType,resources.get(resourceType))) return false;
        }
        return true;
    }

    default boolean checkResourcesFromStrongBox(Warehouse warehouse, Map<ResourceType,Integer> resources) {
        for(ResourceType resourceType : resources.keySet()) {
            if(!warehouse.checkResourceFromStrongBox(resourceType, resources.get(resourceType))) return false;
        }
        return true;
    }

    default boolean checkResourcesFromExtraDepots(Warehouse warehouse, Map<ResourceType, Integer> resources) {
        for(ResourceType resourceType : resources.keySet()) {
            if(!warehouse.checkResourceFromExtraDepot(resourceType, resources.get(resourceType))) return false;
        }
        return true;
    }

    default void takeResourcesFromNormalDepots(Warehouse warehouse, Map<ResourceType,Integer> resources) throws DepotNotFoundException, DepotOutOfBoundsException{
        for(ResourceType resourceType : resources.keySet()) {
            warehouse.takeResourceFromNormalDepot(resourceType,resources.get(resourceType));
        }
    }
    default void takeResourcesFromExtraDepots(Warehouse warehouse, Map<ResourceType,Integer> resources) throws DepotOutOfBoundsException, DepotNotFoundException {
        for(ResourceType resourceType : resources.keySet()) {
            warehouse.takeResourceFromExtraDepot(resourceType, resources.get(resourceType));
        }
    }

    default void takeResourcesFromStrongbox(Warehouse warehouse, Map<ResourceType,Integer> resources) throws StrongboxOutOfBoundException {
        warehouse.takeResourcesFromStrongbox(resources);
    }


}

