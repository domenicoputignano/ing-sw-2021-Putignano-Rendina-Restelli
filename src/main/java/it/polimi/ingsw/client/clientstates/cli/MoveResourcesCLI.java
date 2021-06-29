package it.polimi.ingsw.client.clientstates.cli;

import it.polimi.ingsw.client.clientstates.AbstractMoveResources;
import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.exceptions.InterruptedActionException;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.MoveFromExtraToNormalAction;
import it.polimi.ingsw.utils.MoveFromNormalToExtraAction;
import it.polimi.ingsw.utils.MoveFromNormalToNormalAction;

import java.util.Scanner;

/**
 * This class represents the CLI-specific state that is reached when the client wants to move resources in his depots.
 */
public class MoveResourcesCLI extends AbstractMoveResources {

    /**
     * The Scanner used to receive inputs from user.
     */
    private final Scanner input = new Scanner(System.in);
    /**
     * The CLI instance this state refers to.
     */
    private final CLI cli;

    /**
     * Initializes reference to CLI and client.
     */
    public MoveResourcesCLI(Client client) {
        super(client);
        this.cli = (CLI) client.getUI();
    }

    /**
     * Main method of the class. It leads the interaction with the user during different phases of this action.
     */
    @Override
    public void manageUserInteraction() {
        chooseSourceDestination();
    }

    /**
     * Method used to handle the choice of the type of depots source and destination of the move action.
     */
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

    /**
     * Method used to parse the user's choice to one of the available types of move actions.
     * @param choice the user's choice.
     * @return whether the user's choice is valid.
     * @throws InterruptedActionException when user can't perform the chosen move.
     */
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

    /**
     * Method used to manage a move action from a normal depot to another normal depot.
     */
    private void manageMoveFromNormalToNormalDepot() {
        System.out.println("You have chosen to move resources from a normal depot to another normal depot");
        System.out.println("Choose the index of the depot source: ");
        int depotIndexSource = cli.askValidDepotIndex(input, 3);
        System.out.println("Choose the index of the depot destination: ");
        int depotIndexDestination = cli.askValidDepotIndex(input, 3);
        messageToSend.setMoveAction(new MoveFromNormalToNormalAction(depotIndexSource, depotIndexDestination));
    }

    /**
     * Method used to manage a move action from a normal depot to an extra depot.
     * @param canChooseExtraDepotIndex if the player has two extra depots active.
     */
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

    /**
     * Method used to manage a move action from an extra depot to a normal depot.
     * @param canChooseExtraDepotIndex if the player has two extra depots active.
     */
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
