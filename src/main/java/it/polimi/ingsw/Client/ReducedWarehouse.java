package it.polimi.ingsw.Client;

import it.polimi.ingsw.Model.ResourceType;

import java.util.Map;

public class ReducedWarehouse {
    private final ReducedDepot[] normalDepots;
    private final ReducedDepot[] extraDepots;
    private final Map<ResourceType, Integer> strongbox;

    public ReducedWarehouse(ReducedDepot[] normalDepots, ReducedDepot[] extraDepots, Map<ResourceType, Integer> strongbox){
        this.normalDepots = normalDepots;
        this.extraDepots = extraDepots;
        this.strongbox = strongbox;
    }

    public ReducedDepot[] getNormalDepots() {
        return normalDepots;
    }

    public ReducedDepot[] getExtraDepots() {
        return extraDepots;
    }

    public Map<ResourceType, Integer> getStrongbox() {
        return strongbox;
    }
}
