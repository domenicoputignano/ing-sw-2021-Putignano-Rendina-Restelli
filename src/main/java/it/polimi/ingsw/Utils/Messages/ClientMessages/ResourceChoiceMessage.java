package it.polimi.ingsw.Utils.Messages.ClientMessages;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Network.RemoteView;
import it.polimi.ingsw.Utils.Pair;

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
        if(chosenResources.size() > 1 && chosenResources.get(0).getKey() == chosenResources.get(1).getKey() &&
                ((!chosenResources.get(0).getValue().equals(chosenResources.get(1).getValue())) || (chosenResources.get(0).getValue().equals(chosenResources.get(1).getValue())
        && chosenResources.get(0).getValue().equals(1)))) return false;
        return true;
    }

    public List<Pair<ResourceType, Integer>> getChosenResources() {
        return chosenResources;
    }
}
