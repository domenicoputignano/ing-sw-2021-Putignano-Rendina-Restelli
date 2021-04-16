package it.polimi.ingsw.Utils;

import it.polimi.ingsw.Model.ActiveProductions;
import it.polimi.ingsw.Model.ResourceType;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class ActivateProductionMessage {
    private ActiveProductions productions;
    private ResourceType input1;
    private ResourceType input2;
    private ResourceType output;
    private ResourceType outputExtra1;
    private ResourceType outputExtra2;
    private Map<String, EnumMap<ResourceType,Integer>> howToTakeResources = new HashMap<>();

    public ActiveProductions getProductions() {
        return productions;
    }

    public ResourceType getInput1() {
        return input1;
    }

    public ResourceType getInput2() {
        return input2;
    }

    public ResourceType getOutput() {
        return output;
    }

    public ResourceType getOutputExtra1() {
        return outputExtra1;
    }

    public ResourceType getOutputExtra2() {
        return outputExtra2;
    }

    public Map<String, EnumMap<ResourceType, Integer>> getHowToTakeResources() {
        return howToTakeResources;
    }

}
