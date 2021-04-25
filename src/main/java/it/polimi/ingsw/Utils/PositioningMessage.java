package it.polimi.ingsw.Utils;

import it.polimi.ingsw.Model.ResourceType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PositioningMessage implements ClientMessage {
    List<Pair<ResourceType, MarbleDestination>> whereToPutResources = new ArrayList<>();

    public List<Pair<ResourceType, MarbleDestination>> getWhereToPutResources() {
        return whereToPutResources;
    }

    public void setWhereToPutResources(List<Pair<ResourceType, MarbleDestination>> whereToPutResources) {
        this.whereToPutResources = whereToPutResources;
    }
    public boolean isValidMessage()
    {
        if(whereToPutResources.stream().anyMatch(x -> x.getKey() == null || x.getValue() == null)) return false;
        return true;
    }

    public List<ResourceType> getResourcesToPut()
    {
        return whereToPutResources.stream().map(x -> x.getKey()).collect(Collectors.toList());
    }
}
