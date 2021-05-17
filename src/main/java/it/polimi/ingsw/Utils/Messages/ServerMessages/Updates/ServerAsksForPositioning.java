package it.polimi.ingsw.Utils.Messages.ServerMessages.Updates;

import it.polimi.ingsw.Client.ClientStatesController;
import it.polimi.ingsw.Client.ReducedPersonalBoard;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Network.Client;
import java.util.List;

public class ServerAsksForPositioning extends UpdateMessage {
    private List<ResourceType> resourcesToSettle;

    public ServerAsksForPositioning(User user, ReducedPersonalBoard reducedPersonalBoard, List<ResourceType> pendingResources)
    {
        this.user = user;
        this.userPersonalBoard = reducedPersonalBoard;
        this.resourcesToSettle = pendingResources;
    }

    @Override
    public void handleMessage(Client handler) {
        handler.getGame().updatePersonalBoard(this);
        handler.getUI().render(this);
        ClientStatesController.updateClientState(this, handler.getUI());
    }

    public List<ResourceType> getResourcesToSettle() {
        return resourcesToSettle;
    }
}
