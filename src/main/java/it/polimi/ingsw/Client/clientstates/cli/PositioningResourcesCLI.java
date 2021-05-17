package it.polimi.ingsw.Client.clientstates.cli;

import it.polimi.ingsw.Client.clientstates.AbstractPositioningResources;
import it.polimi.ingsw.Client.view.CLI;
import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.MarbleDestination;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;
import it.polimi.ingsw.Utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class PositioningResourcesCLI extends AbstractPositioningResources {

    private final CLI cli;

    public PositioningResourcesCLI(Client client, List<ResourceType> resourcesToSettle) {
        super(client, resourcesToSettle);
        this.cli = (CLI) client.getUI();
    }

    @Override
    public void render(ServerMessage message) {

    }

    @Override
    public void manageUserInteraction() {
        List<Pair<ResourceType, MarbleDestination>> whereToPutResources = new ArrayList<>();
        for(ResourceType resource : resourcesToSettle) {
            System.out.println("You have a " + resource + "to settle.");
            whereToPutResources.add(new Pair<>(resource, cli.chooseMarbleDestination()));
        }
        messageToSend.setWhereToPutResources(whereToPutResources);
        client.sendMessage(messageToSend);
    }
}
