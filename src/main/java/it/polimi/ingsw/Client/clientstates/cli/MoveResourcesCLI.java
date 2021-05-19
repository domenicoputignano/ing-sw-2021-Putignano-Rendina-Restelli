package it.polimi.ingsw.Client.clientstates.cli;

import it.polimi.ingsw.Client.clientstates.AbstractMoveResources;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;

import java.util.Scanner;

public class MoveResourcesCLI extends AbstractMoveResources {

    private final Scanner input = new Scanner(System.in);

    public MoveResourcesCLI(Client client) {
        super(client);
    }

    @Override
    public void render(ServerMessage message) {

    }

    @Override
    public void manageUserInteraction() {

    }

    private void chooseSourceDestination() {
        System.out.println("Choose the depot source of your move action and its index. [DEPOT|EXTRADEPOT],[Index]" );
        String choice;
        boolean choiceOK = false;
        do {
            choice = input.next().toUpperCase();
            parseSourceDestinationChoice(choice);
        } while(!choiceOK);

    }

    private void parseSourceDestinationChoice(String choice) {

    }
}
