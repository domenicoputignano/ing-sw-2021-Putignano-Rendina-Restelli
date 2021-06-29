package it.polimi.ingsw.client.clientstates.cli;

import it.polimi.ingsw.client.clientstates.AbstractActivateProduction;
import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.exceptions.BackToMenuException;
import it.polimi.ingsw.exceptions.InterruptedActionException;
import it.polimi.ingsw.network.Client;

import java.util.InputMismatchException;
import java.util.Scanner;

import static it.polimi.ingsw.client.view.UI.fromStringToResourceType;

/**
 * This class represents the CLI-specific state that is reached when the player in turn
 * wants to activate productions during his turn.
 */
public class ActivateProductionCLI extends AbstractActivateProduction {

    /**
     * The Scanner used to receive inputs from user.
     */
    private final Scanner input = new Scanner(System.in);
    /**
     * The CLI instance this state refers to.
     */
    private final CLI cli;
    /**
     * String used as a support containing different answers from the user during the action.
     */
    private String answer;

    /**
     * Initializes references to CLI and client.
     */
    public ActivateProductionCLI(Client client) {
        super(client);
        cli = (CLI) client.getUI();
    }

    /**
     * Main method of the class. It leads the interaction with the user during different phases of this action.
     */
    @Override
    public void manageUserInteraction() {
        try {
            // prints the available slots
            cli.printSlots(client.getGame().getPlayer(client.getUser()).getPersonalBoard());
            // if the player wants to go back, return to the action choice state
            cli.playerWantsToGoBack();
            // select which productions the player wants to activate
            selectProductions();
            // checks whether the requested productions are valid
            if(areValidRequestedProductions()){
                System.out.println("Available resources are shown below\n"+
                        client.getGame().getPlayer(client.getUser()).getPersonalBoard().getWarehouse());
                // select where to take the resources needed to activate the requested productions
                resourcesChoice();
                if(checkRequiredResources()) {
                    messageToSend.setHowToTakeResources(cli.askInstructionsOnHowToTakeResources(calculateInputResources()));
                }
                else {
                    System.out.println("You cannot activate chosen production because you don't have enough resources");
                    throw new InterruptedActionException();
                }
            }
            else {
                System.out.println("Selected productions are not available, try again");
                throw new InterruptedActionException();
            }
            // send the compiled message to the server
            client.sendMessage(messageToSend);
        } catch(InterruptedActionException e) {
            cli.returnToActionBeginning(new ActivateProductionCLI(this.client));
        } catch (BackToMenuException e) {
            cli.returnToMenu();
        }
    }

    /**
     * Method which handles the selection of productions the player wants to activate.
     * It runs until the selection is valid.
     */
    private void selectProductions() {
        boolean doneSelection = false;
        System.out.println("Choose which productions you want to activate (please type yes/no) for each one.");
        for(int i = 0; i < 4; ) {
            // asks if the player wants to activate basic productions
            do {
                System.out.print("Basic: ");
                if(i == 0){
                    try {
                        String choice = input.next();
                        if (choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("no")) {
                            requiredProduction.setBasic(choice.equalsIgnoreCase("yes"));
                            doneSelection = true;
                            i++;
                        } else {
                            throw new InputMismatchException();
                        }
                    } catch(InputMismatchException e) {
                        System.out.println("Invalid choice, please select yes/no for each slot");
                    }
                }
            } while (!doneSelection);
            doneSelection = false;
            // asks if the player wants to activate production on first slot
            do {
                if(i==1){
                    System.out.print("Slot 1: ");
                    try {
                        String choice = input.next();
                        if (choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("no")) {
                            requiredProduction.setSlot1(choice.equalsIgnoreCase("yes"));
                            doneSelection = true;
                            i++;
                        } else {
                            throw new InputMismatchException();
                        }
                    } catch(InputMismatchException e) {
                        System.out.println("Invalid choice, please select yes/no for slot 1");
                    }
                }
            } while(!doneSelection);
            doneSelection = false;
            // asks if the player wants to activate production on second slot
            do {
                if(i==2){
                    System.out.print("Slot 2: ");
                    try {
                        String choice = input.next();
                        if (choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("no")) {
                            requiredProduction.setSlot2(choice.equalsIgnoreCase("yes"));
                            doneSelection = true;
                            i++;
                        } else {
                            throw new InputMismatchException();
                        }
                    } catch(InputMismatchException e) {
                        System.out.println("Invalid choice, please select yes/no for slot 2");
                    }
                }
            } while(!doneSelection);
            doneSelection = false;
            // asks if the player wants to activate production on third slot
            do {
                if(i==3){
                    System.out.print("Slot 3: ");
                    try {
                        String choice = input.next();
                        if (choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("no")) {
                            requiredProduction.setSlot3(choice.equalsIgnoreCase("yes"));
                            doneSelection = true;
                            i++;
                        } else {
                            throw new InputMismatchException();
                        }
                    } catch(InputMismatchException e) {
                        System.out.println("Invalid choice, please select yes/no for slot 3");
                    }
                }
            } while(!doneSelection);
        }
        // asks if the player wants to activate first extra production, if it is available
        if(listExtraProductionEffect().size() == 1) {
            do {
                System.out.printf("One extra production of type %s available, do you want to activate it? (yes/no) ", getExtraProductionType(0));
                answer = input.next();
            } while(!isValidChoice(answer));
            requiredProduction.setExtraSlot1(answer.equalsIgnoreCase("yes"));
            requiredProduction.setExtraSlot2(false);
        }
        // asks if the player wants to activate second extra production, if it is available
        if(listExtraProductionEffect().size() == 2) {
            do {
                System.out.printf("Two extra productions available, one of type: %s, do you want to activate it? (yes/no) ", getExtraProductionType(0));
                answer = input.next();
            } while (!isValidChoice(answer));
            requiredProduction.setExtraSlot1(answer.equalsIgnoreCase("yes"));
            do {
                System.out.printf("Second is of type: %s, do you want to activate it? (yes/no) ", getExtraProductionType(1));
                answer = input.next();
            } while (!isValidChoice(answer));
            requiredProduction.setExtraSlot2(answer.equalsIgnoreCase("yes"));
        }
        // sets the message with the required productions
        messageToSend.setProductions(requiredProduction);
    }

    /**
     * Utility method used to check if the answer inserted by the user is valid.
     * @param choice the user's answer.
     * @return the check validity.
     */
    private boolean isValidChoice(String choice) {
        return choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("no");
    }


    /**
     * Method which handles the selection of resources for the productions which need this selection.
     */
    private void resourcesChoice() {
        if(requiredProduction.isBasic()) {
            System.out.print("You chose basic production power, please select two input resources " +
                    "and one output resource [Coin (C), Servant(SE), Shield(SH), Stone(ST)]\nChoose first input: ");
            messageToSend.setInput1(askValidResource());
            System.out.print("\nChoose second input: ");
            messageToSend.setInput2(askValidResource());
            System.out.print("\nChoose output: ");
            messageToSend.setOutput(askValidResource());
        }
        if(requiredProduction.isExtraSlot1()) {
            System.out.println("You chose first extra production effect, select the output [Coin (C), Servant(SE), Shield(SH), Stone(ST)]: ");
            messageToSend.setOutputExtra1(askValidResource());
        }
        if(requiredProduction.isExtraSlot2()) {
            System.out.println("You chose second extra production effect, select the output [Coin (C), Servant(SE), Shield(SH), Stone(ST)]: ");
            messageToSend.setOutputExtra2(askValidResource());
        }
    }

    /**
     * Method used to handle the request of a {@link ResourceType} to the user whenever this request is needed.
     * @return the Resource Type chosen by the user.
     */
    public ResourceType askValidResource() {
        boolean done = false;
        ResourceType resource = null;
        while(!done) {
            answer = input.next();
            resource = fromStringToResourceType(answer);
            if(resource!=null){
                done = true;
            }
        }
        return resource;
    }


}
