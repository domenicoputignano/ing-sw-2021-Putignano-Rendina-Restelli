package it.polimi.ingsw.Utils.Messages.ServerMessages.Updates;

import it.polimi.ingsw.Client.ReducedMarble;
import it.polimi.ingsw.Client.ReducedMarketTray;
import it.polimi.ingsw.Model.ResourceType;
import it.polimi.ingsw.Network.Client;

import java.util.ArrayList;
import java.util.List;

public class TakeResourcesFromMarketUpdate extends UpdateMessage {

    private ReducedMarketTray resultingMarketTray;
    private List<ResourceType> earnedResources = new ArrayList<>();

    @Override
    public void handleUpdateMessage(Client client) {

    }
}
