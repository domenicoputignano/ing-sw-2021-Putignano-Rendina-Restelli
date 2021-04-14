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
    private Map<String, EnumMap<ResourceType,Integer>> neededResources = new HashMap<>();
}
