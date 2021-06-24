package it.polimi.ingsw.Model;

import it.polimi.ingsw.Commons.ResourceType;

import java.io.Serializable;
import java.util.Map;

/**
 * This class represents how a certain development card is able to convert resources.
 * @see it.polimi.ingsw.Commons.DevelopmentCard
 */
public class ProductionRule implements Serializable {

    /**
     * Resources needed to activated production relating to the card.
     */
    private Map<ResourceType, Integer> inputResources;
    /**
     * Resources gained after the activation of a card.
     */
    private Map<ResourceType, Integer> outputResources;
    /**
     * Faith points that allows the player to move forward in the faith track.
     */
    private int outputFaith;

    /**
     * Initializes and empty production rule.
     */
    public ProductionRule(){
        this.inputResources = null;
        this.outputResources = null;
        this.outputFaith = 0;
    }

    /**
     * Initializes a production rule.
     * @param inputResources resources that have to be converted.
     * @param outputResources resulting resources.
     * @param outputFaith faith points gained.
     */
    public ProductionRule(Map<ResourceType, Integer> inputResources, Map<ResourceType, Integer> outputResources, int outputFaith) {
        this.inputResources = inputResources;
        this.outputResources = outputResources;
        this.outputFaith = outputFaith;
    }

    public Map<ResourceType, Integer> getInputResources() {
        return inputResources;
    }

    public Map<ResourceType, Integer> getOutputResources() {
        return outputResources;
    }

    public int getOutputFaith() {
        return outputFaith;
    }

    public void setInputResources(Map<ResourceType, Integer> inputResources) {
        this.inputResources = inputResources;
    }

    public void setOutputResources(Map<ResourceType, Integer> outputResources) {
        this.outputResources = outputResources;
    }

    public void setOutputFaith(int outputFaith) {
        this.outputFaith = outputFaith;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductionRule that = (ProductionRule) o;
        return outputFaith == that.outputFaith && inputResources.equals(that.inputResources) && outputResources.equals(that.outputResources);
    }

}
