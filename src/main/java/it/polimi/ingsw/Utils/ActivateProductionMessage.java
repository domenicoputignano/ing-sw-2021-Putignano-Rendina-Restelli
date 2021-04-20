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
    private Map<ResourceSource, EnumMap<ResourceType,Integer>> howToTakeResources = new HashMap<>();

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

    public Map<ResourceSource, EnumMap<ResourceType, Integer>> getHowToTakeResources() {
        return howToTakeResources;
    }

    public boolean isValidMessage()
    {
        if(productions == null) return false;
        if(productions.isBasic() && (input1 == null || input2 == null || output == null)) return false;
        if(productions.isExtraSlot1() && (outputExtra1 == null)) return false;
        if(productions.isExtraSlot2() && (outputExtra2 == null)) return false;
        // TODO : fare metodo per evitare le duplicazioni
        if(howToTakeResources.size() != 3 || howToTakeResources.keySet().stream().anyMatch(x -> howToTakeResources.get(x) == null) ||
                howToTakeResources.keySet().stream().anyMatch(x -> howToTakeResources.get(x).keySet().stream().anyMatch(y -> howToTakeResources.get(x).get(y) < 0)))
            return false;
        return true;
    }
}
