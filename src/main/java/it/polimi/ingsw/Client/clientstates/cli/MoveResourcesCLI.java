package it.polimi.ingsw.Client.clientstates.cli;

import it.polimi.ingsw.Client.clientstates.AbstractMoveResources;
import it.polimi.ingsw.Client.view.CLI;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;
import it.polimi.ingsw.Utils.MoveFromExtraToNormalAction;
import it.polimi.ingsw.Utils.MoveFromNormalToExtraAction;
import it.polimi.ingsw.Utils.MoveFromNormalToNormalAction;

import java.util.Scanner;

public class MoveResourcesCLI extends AbstractMoveResources {

    private final Scanner input = new Scanner(System.in);
    private final CLI cli;

    public MoveResourcesCLI(Client client) {
        super(client);
        this.cli = (CLI) client.getUI();
    }

    @Override
    public void render(ServerMessage message) {

    }

    @Override
    public void manageUserInteraction() {
        chooseSourceDestination();
    }

    private void chooseSourceDestination() {
        System.out.println("Choose the type of depot source and destination of your move action. FROM [DEPOT|EXTRADEPOT], TO [DEPOT|EXTRADEPOT]");
        String choice;
        boolean choiceOK = false;
        choice = input.next().toUpperCase();
        parseSourceDestinationChoice(choice);
        client.sendMessage(messageToSend);
    }

    private void parseSourceDestinationChoice(String choice) {
        switch(choice) {
            case "DEPOT,DEPOT": {
                manageMoveFromNormalToNormalDepot();
                break;
            }
            case "DEPOT,EXTRADEPOT": {
                if(getNumOfExtraDepots() == 0) {
                    System.out.println("You can't move resources to an extradepot because you don't have one.");
                    cli.returnToActionChoice();
                } else if(getNumOfExtraDepots() == 1) {
                    manageMoveFromNormalToExtraDepot(false);
                } else {
                    manageMoveFromNormalToExtraDepot(true);
                }
                break;
            }
            case "EXTRADEPOT, DEPOT": {
                if(getNumOfExtraDepots() == 0) {
                    System.out.println("You can't move resources to an extradepot because you don't have one.");
                    cli.returnToActionChoice();
                } else if(getNumOfExtraDepots() == 1) {
                    manageMoveFromExtraToNormalDepot(false);
                } else {
                    manageMoveFromExtraToNormalDepot(true);
                }
                break;
            }
        }
    }

    private void manageMoveFromNormalToNormalDepot() {
        System.out.println("You have chosen to move resources from a normal depot to another normal depot");
        System.out.println("Choose the index of the depot source: ");
        int depotIndexSource = cli.askValidDepotIndex(input, 3);
        System.out.println("Choose the index of the depot destination: ");
        int depotIndexDestination = cli.askValidDepotIndex(input, 3);
        messageToSend.setMoveAction(new MoveFromNormalToNormalAction(depotIndexSource, depotIndexDestination));
    }

    private void manageMoveFromNormalToExtraDepot(boolean canChooseExtraDepotIndex) {
        System.out.println("You have chosen to move resources from a normal depot to an extra depot");
        System.out.println("Choose the index of the depot source: [1-3]");
        int depotIndexSource = cli.askValidDepotIndex(input, 3);
        int extraDepotIndex;
        if(canChooseExtraDepotIndex) {
            System.out.println("Choose the index of the extraDepot destination: [1-2]");
            extraDepotIndex = cli.askValidDepotIndex(input, 2);
        } else {
            System.out.println("You have only one extra depot active so it will be the destination of your move action");
            extraDepotIndex = 1;
        }
        System.out.println("How many resources do you want to move? [1-2]");
        int occ = cli.askValidOcc(input, 2);
        messageToSend.setMoveAction(new MoveFromNormalToExtraAction(depotIndexSource, occ, extraDepotIndex));
    }

    private void manageMoveFromExtraToNormalDepot(boolean canChooseExtraDepotIndex) {
        System.out.println("You chose to move resources from an extra depot to a normal one");
        int extraDepotIndex;
        if(canChooseExtraDepotIndex) {
            System.out.println("Choose the index of the extraDepot source: [1-2]");
            extraDepotIndex = cli.askValidDepotIndex(input, 2);
        } else {
            System.out.println("You have only one extra depot active so it will be the source of your move action");
            extraDepotIndex = 1;
        }
        System.out.println("Choose the index of the depot destination: [1-3]");
        int depotIndexDestination = cli.askValidDepotIndex(input, 3);
        System.out.println("How many resources do you want to move? [1-2]");
        int occ = cli.askValidOcc(input, 2);
        messageToSend.setMoveAction(new MoveFromExtraToNormalAction(extraDepotIndex, occ, depotIndexDestination));
    }
}
