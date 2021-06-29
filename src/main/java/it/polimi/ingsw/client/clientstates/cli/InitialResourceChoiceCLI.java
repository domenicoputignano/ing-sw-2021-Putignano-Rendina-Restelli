package it.polimi.ingsw.client.clientstates.cli;

import it.polimi.ingsw.client.clientstates.AbstractInitialResourceChoice;
import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.clientMessages.ResourceChoiceMessage;
import it.polimi.ingsw.utils.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class represents the CLI-specific state that is reached when the client has to
 * choose the initial resources. This state is reached only in a Multiplayer Mode game
 * by second, third and fourth players.
 */
public class InitialResourceChoiceCLI extends AbstractInitialResourceChoice {

    /**
     * The Scanner used to receive inputs from user.
     */
    Scanner input = new Scanner(System.in);
    /**
     * The CLI instance this state refers to.
     */
    private final CLI cli;

    /**
     * Initializes references to CLI and client.
     */
    public InitialResourceChoiceCLI(Client client) {
        super(client);
        this.cli = (CLI) client.getUI();
    }

    /**
     * Main method of the class. It leads the interaction with the user asking which resource he wants to take and
     * where he wants to position it.
     */
    @Override
    public void manageUserInteraction() {
        System.out.printf("You are the %d° player, you can choose %d resource to place in your depots\n",
                client.getUserPosition(), client.getUserPosition()/2);
        ResourceType resource;
        int depotIndex;
        List<Pair<ResourceType, Integer>> requiredResources = new ArrayList<>();
        // the number of resources to choose depends on the user's position
        for(int i = 0; i < client.getUserPosition()/2; i++) {
            System.out.print("Select resource [Coin(C), Servant(SE), Shield(SH), Stone(ST)]: ");
            resource = cli.askValidResource(input);
            System.out.print("Select index of depot that will contain selected resource [1 - 3]: ");
            depotIndex = cli.askValidDepotIndex(input, 3);
            requiredResources.add(new Pair<>(resource,depotIndex));
        }
        // compile the message as chosen and send it to the server, then pass to the WaitForTurn state
        messageToSend = new ResourceChoiceMessage(requiredResources);
        client.sendMessage(messageToSend);
        cli.changeCliState(new WaitForTurnCLI(client));
        cli.manageUserInteraction();
    }
}
