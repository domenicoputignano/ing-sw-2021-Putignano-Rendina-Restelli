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

public class InitialResourceChoiceCLI extends AbstractInitialResourceChoice {

    private final CLI cli;
    Scanner input = new Scanner(System.in);

    public InitialResourceChoiceCLI(Client client) {
        super(client);
        this.cli = (CLI) client.getUI();
    }

    @Override
    public void manageUserInteraction() {
        System.out.printf("You are the %d° player, you can choose %d resource to place in your depots\n",
                client.getUserPosition(), client.getUserPosition()/2);
        ResourceType resource;
        int depotIndex;
        List<Pair<ResourceType, Integer>> requiredResources = new ArrayList<>();
        for(int i = 0; i < client.getUserPosition()/2; i++) {
            System.out.print("Select resource [Coin(C), Servant(SE), Shield(SH), Stone(ST)]: ");
            resource = cli.askValidResource(input);
            System.out.print("Select index of depot that will contain selected resource [1 - 3]: ");
            depotIndex = cli.askValidDepotIndex(input, 3);
            requiredResources.add(new Pair<>(resource,depotIndex));
        }
        messageToSend = new ResourceChoiceMessage(requiredResources);
        client.sendMessage(messageToSend);
        cli.changeCliState(new WaitForTurnCLI(client));
        cli.manageUserInteraction();
    }
}
