package it.polimi.ingsw.client.clientstates.cli;

import it.polimi.ingsw.client.clientstates.AbstractPositioningResources;
import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.MarbleDestination;
import it.polimi.ingsw.utils.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the CLI-specific state reached when the client fails to position some resources
 * and the server sends a {@link it.polimi.ingsw.utils.messages.serverMessages.Updates.ServerAsksForPositioning}.
 */
public class PositioningResourcesCLI extends AbstractPositioningResources {

    /**
     * The CLI instance this state refers to.
     */
    private final CLI cli;

    /**
     * Initializes references to CLI and client.
     */
    public PositioningResourcesCLI(Client client, List<ResourceType> resourcesToSettle) {
        super(client, resourcesToSettle);
        this.cli = (CLI) client.getUI();
    }

    /**
     * Main method of the class. It leads the interaction with the user asking, for each pending resource, where he wants
     * to position it. Then a message containing the compiled instructions is sent to the server.
     */
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
