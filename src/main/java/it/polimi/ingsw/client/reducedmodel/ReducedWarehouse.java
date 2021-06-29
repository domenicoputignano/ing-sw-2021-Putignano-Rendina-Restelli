package it.polimi.ingsw.client.reducedmodel;

import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.utils.ANSI_Color;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
/**
 * This class represents a simplified version of a Warehouse with respect to the server's class.
 * It contains only the information required client side.
 */
public class ReducedWarehouse implements Serializable {
    /**
     * Attributes that represent the contents of the Warehouse
     */
    private final ReducedDepot[] normalDepots;
    private final ReducedDepot[] extraDepots;
    private final ReducedStrongbox strongbox;
    private final Map<ResourceType, Integer> availableResources;
    /**
     * Initialize an instance by setting normalDepots ,extraDepots,strongbox, and availableResources of the Strongbox.
     */
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

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Depots\n");
        for(ReducedDepot d : normalDepots)
            result.append((Arrays.asList(normalDepots).indexOf(d)+1)+" : ")
                    .append(" ").append(d).append(ANSI_Color.RESET).append('\n');
        for(ReducedDepot extraD : extraDepots)
            if (extraD != null){
                result.append("Extra depot of type ").append(extraD.getType()).append(ANSI_Color.RESET).append(" :");
                result.append(extraD).append(ANSI_Color.RESET).append("\n");
            }
        result.append("Strongbox\n").append(strongbox);
        return String.valueOf(result);
    }

}
