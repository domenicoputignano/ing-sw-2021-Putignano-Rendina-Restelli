package it.polimi.ingsw.utils.messages.clientMessages;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.network.RemoteView;
import it.polimi.ingsw.utils.Pair;

import java.util.ArrayList;
import java.util.List;


public class ResourceChoiceMessage implements GameControllerHandleable {

    private final List<Pair<ResourceType,Integer>> chosenResources = new ArrayList<>();

    public ResourceChoiceMessage(List<Pair<ResourceType,Integer>> chosenResources) {
        this.chosenResources.addAll(chosenResources);
    }

    public void handleMessage(GameController gameController, RemoteView sender) {
        gameController.handleResourceChoiceMessage(this, sender);
    }


    //method that has to be performed by Controller before invoking handleMessage
    public boolean isValidMessage() {
        if(chosenResources.size() > 2 || chosenResources.size() == 0) return false;
        if(chosenResources.stream().anyMatch(x -> x.getValue() < 1 || x.getValue() > 3 || x.getKey() == null)) return false;
        return true;
    }

    public List<Pair<ResourceType, Integer>> getChosenResources() {
        return chosenResources;
    }


}
