package it.polimi.ingsw.Client.clientstates.cli;

import it.polimi.ingsw.Client.clientstates.AbstractActivateProduction;
import it.polimi.ingsw.Client.view.CLI;
import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Exceptions.BackToMenuException;
import it.polimi.ingsw.Exceptions.InterruptedActionException;
import it.polimi.ingsw.Network.Client;

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
        System.out.println("Choose which productions you want to activate");
        System.out.println("Basic\tSlot1\tSlot2\tSlot3");
        for(int i = 0; i < 4; i++) {
            if(i == 0) requiredProduction.setBasic(input.nextBoolean());
            if(i == 1) requiredProduction.setSlot1(input.nextBoolean());
            if(i == 2) requiredProduction.setSlot2(input.nextBoolean());
            if(i == 3) requiredProduction.setSlot3(input.nextBoolean());
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
