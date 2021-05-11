package it.polimi.ingsw.Model;

import it.polimi.ingsw.Commons.ResourceType;

import java.util.Map;


public class ProductionRule {

    private Map<ResourceType, Integer> inputResources;
    private Map<ResourceType, Integer> outputResources;
    private int outputFaith;

    public ProductionRule(){
        this.inputResources = null;
        this.outputResources = null;
        this.outputFaith = 0;
    }

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
