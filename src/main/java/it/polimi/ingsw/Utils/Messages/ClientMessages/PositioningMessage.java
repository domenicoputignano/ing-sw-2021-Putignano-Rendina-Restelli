package it.polimi.ingsw.Utils.Messages.ClientMessages;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.TurnController;
import it.polimi.ingsw.Model.ResourceType;
import it.polimi.ingsw.Network.RemoteView;
import it.polimi.ingsw.Utils.MarbleDestination;
import it.polimi.ingsw.Utils.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PositioningMessage implements TurnControllerHandleable {
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

    public void handleMessage(TurnController turnController, RemoteView sender) {
        turnController.handlePositioningMessage(this,sender);
    }

    public void handleMessage(GameController gameController, RemoteView sender) {
        handleMessage(gameController.getTurnController(), sender);
    }

}
