package it.polimi.ingsw.Client;

import it.polimi.ingsw.Commons.ResourceType;

import java.util.Map;

public class ReducedWarehouse {
    private final ReducedDepot[] normalDepots;
    private final ReducedDepot[] extraDepots;
    private final ReducedStrongbox strongbox;
    private final Map<ResourceType, Integer> availableResources;

    public ReducedWarehouse(ReducedDepot[] normalDepots, ReducedDepot[] extraDepots, ReducedStrongbox strongbox, Map<ResourceType,Integer> availableResources){
        this.normalDepots = normalDepots;
        this.extraDepots = extraDepots;
        this.strongbox = strongbox;
        this.availableResources = availableResources;
    }

    public ReducedDepot[] getNormalDepots() {
        return normalDepots;
    }

    public ReducedDepot[] getExtraDepots() {
        return extraDepots;
    }

    public ReducedStrongbox getStrongbox() {
        return strongbox;
    }

    public Map<ResourceType, Integer> getAvailableResources() {
        return availableResources;
    }
}
