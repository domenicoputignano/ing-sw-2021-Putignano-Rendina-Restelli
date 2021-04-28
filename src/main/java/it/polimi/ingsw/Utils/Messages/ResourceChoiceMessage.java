package it.polimi.ingsw.Utils.Messages;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.ResourceType;
import it.polimi.ingsw.Utils.Pair;

import java.util.ArrayList;
import java.util.List;


public class ResourceChoiceMessage implements GameControllerHandleable {

    private List<Pair<ResourceType,Integer>> chosenResources = new ArrayList<>();

    public void handleMessage(GameController gameController, Player sender) {

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
