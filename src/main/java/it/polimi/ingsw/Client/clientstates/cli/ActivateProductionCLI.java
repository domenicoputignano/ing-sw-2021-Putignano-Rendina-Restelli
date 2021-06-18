package it.polimi.ingsw.Client.clientstates.cli;

import it.polimi.ingsw.Client.clientstates.AbstractActivateProduction;
import it.polimi.ingsw.Client.view.CLI;
import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Exceptions.BackToMenuException;
import it.polimi.ingsw.Exceptions.InterruptedActionException;
import it.polimi.ingsw.Network.Client;

import java.util.InputMismatchException;
import java.util.Scanner;

import static it.polimi.ingsw.Client.view.UI.fromStringToResourceType;


public class ActivateProductionCLI extends AbstractActivateProduction {

    private final CLI cli;
    private String answer;
    private final Scanner input = new Scanner(System.in);


    public ActivateProductionCLI(Client client) {
        super(client);
        cli = (CLI) client.getUI();
    }


    @Override
    public void manageUserInteraction() {
        boolean doneSelection = false;
        try {
            cli.printSlots(client.getGame().getPlayer(client.getUser()).getPersonalBoard());
            cli.playerWantsToGoBack();
            selectProductions();
            if(areValidRequestedProductions()){
                System.out.println("Available resources are shown below\n"+
                        client.getGame().getPlayer(client.getUser()).getPersonalBoard().getWarehouse());
                resourcesChoice();
                if(checkRequiredResources()) {
                    messageToSend.setHowToTakeResources(cli.askInstructionsOnHowToTakeResources(calculateInputResources()));
                    doneSelection = true;
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
            client.sendMessage(messageToSend);
        } catch(InterruptedActionException e) {
            cli.returnToActionBeginning(new ActivateProductionCLI(this.client));
        } catch (BackToMenuException e) {
            cli.returnToMenu();
        }
    }



    private void selectProductions() {
        boolean doneSelection = false;
        System.out.println("Choose which productions you want to activate (please type yes/no) for each one.");
        for(int i = 0; i < 4; ) {
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
        if(listExtraProductionEffect().size() == 1) {
            System.out.printf("One extra production of type %s available, do you want to activate it? (yes/no) ", getExtraProductionType(0));
            answer = input.nextLine();
            requiredProduction.setExtraSlot1(answer.equalsIgnoreCase("yes"));
            requiredProduction.setExtraSlot2(false);
        }
        if(listExtraProductionEffect().size() == 2) {
            System.out.printf("Two extra productions available, one of type: %s, do you want to activate it? (yes/no) ", getExtraProductionType(0));
            answer = input.nextLine();
            requiredProduction.setExtraSlot1(answer.equalsIgnoreCase("yes"));
            System.out.printf("Second is of type: %s, do you want to activate it? (yes/no) ", getExtraProductionType(1));
            answer = input.nextLine();
            requiredProduction.setExtraSlot2(answer.equalsIgnoreCase("yes"));
        }
        messageToSend.setProductions(requiredProduction);
    }

    private boolean parseValidChoice(String choice) {
        if (choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("no")) {
            requiredProduction.setBasic(choice.equalsIgnoreCase("yes"));
        } else throw new InputMismatchException();
        return true;
    }


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
