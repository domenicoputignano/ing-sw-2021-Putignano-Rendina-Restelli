package it.polimi.ingsw.client.clientstates.cli;

import it.polimi.ingsw.client.clientstates.AbstractMoveResources;
import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.exceptions.InterruptedActionException;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.MoveFromExtraToNormalAction;
import it.polimi.ingsw.utils.MoveFromNormalToExtraAction;
import it.polimi.ingsw.utils.MoveFromNormalToNormalAction;

import java.util.Scanner;

public class MoveResourcesCLI extends AbstractMoveResources {

    private final Scanner input = new Scanner(System.in);
    private final CLI cli;

    public MoveResourcesCLI(Client client) {
        super(client);
        this.cli = (CLI) client.getUI();
    }

    @Override
    public void manageUserInteraction() {
        chooseSourceDestination();
    }

    private void chooseSourceDestination() {
        String choice;
        boolean choiceOK;
        try {
            do {
                System.out.println("Choose the type of depot source and destination of your move action. FROM [DEPOT|EXTRADEPOT], TO [DEPOT|EXTRADEPOT]");
                choice = input.next().toUpperCase();
                choiceOK = parseSourceDestinationChoice(choice);
            } while (!choiceOK);
            client.sendMessage(messageToSend);
        } catch (InterruptedActionException e) {
            cli.returnToActionBeginning(new MoveResourcesCLI(this.client));
        }
    }

    private boolean parseSourceDestinationChoice(String choice) throws InterruptedActionException {
        switch(choice.toUpperCase()) {
            case "DEPOT,DEPOT": {
                manageMoveFromNormalToNormalDepot();
                return true;
            }
            case "DEPOT,EXTRADEPOT": {
                if(getNumOfExtraDepots() == 0) {
                    System.out.println("You can't move resources to an extradepot because you don't have one.");
                    throw new InterruptedActionException();
                } else if(getNumOfExtraDepots() == 1) {
                    manageMoveFromNormalToExtraDepot(false);
                } else {
                    manageMoveFromNormalToExtraDepot(true);
                }
                return true;
            }
            case "EXTRADEPOT, DEPOT": {
                if(getNumOfExtraDepots() == 0) {
                    System.out.println("You can't move resources to an extradepot because you don't have one.");
                    throw new InterruptedActionException();
                } else if(getNumOfExtraDepots() == 1) {
                    manageMoveFromExtraToNormalDepot(false);
                } else {
                    manageMoveFromExtraToNormalDepot(true);
                }
                return true;
            }
            default:
                System.out.println("Invalid choice, please try again.");
                return false;
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
