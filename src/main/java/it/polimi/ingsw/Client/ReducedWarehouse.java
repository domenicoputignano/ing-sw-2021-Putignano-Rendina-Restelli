package it.polimi.ingsw.Client;

public class ReducedWarehouse {
    private final ReducedDepot[] normalDepots;
    private final ReducedDepot[] extraDepots;
    private final ReducedStrongbox strongbox;

    public ReducedWarehouse(ReducedDepot[] normalDepots, ReducedDepot[] extraDepots, ReducedStrongbox strongbox){
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

    public ReducedStrongbox getStrongbox() {
        return strongbox;
    }
}
