package it.polimi.ingsw.client.clientstates.cli;

import it.polimi.ingsw.client.clientstates.AbstractPositioningResources;
import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.MarbleDestination;
import it.polimi.ingsw.utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class PositioningResourcesCLI extends AbstractPositioningResources {

    private final CLI cli;

    public PositioningResourcesCLI(Client client, List<ResourceType> resourcesToSettle) {
        super(client, resourcesToSettle);
        this.cli = (CLI) client.getUI();
    }

    @Override
    public void manageUserInteraction() {
        List<Pair<ResourceType, MarbleDestination>> whereToPutResources = new ArrayList<>();
        for(ResourceType resource : resourcesToSettle) {
            System.out.println("You have a " + resource + " to settle.");
            whereToPutResources.add(new Pair<>(resource, cli.chooseMarbleDestination()));
        }
        messageToSend.setWhereToPutResources(whereToPutResources);
        client.sendMessage(messageToSend);
    }
}
