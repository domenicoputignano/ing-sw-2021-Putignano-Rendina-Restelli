package it.polimi.ingsw.Client.clientstates.cli;

import it.polimi.ingsw.Client.clientstates.AbstractActivateProduction;
import it.polimi.ingsw.Client.view.CLI;
import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.ActivateProductionUpdate;
import java.util.Scanner;



public class ActivateProductionCLI extends AbstractActivateProduction {

    private final CLI cli;
    private String answer;
    private Scanner input = new Scanner(System.in);


    public ActivateProductionCLI(CLI cli) {
        this.cli = cli;
    }

    public void render(ServerMessage message) {
        ActivateProductionUpdate update = (ActivateProductionUpdate) message;
        System.out.print("Player "+update.getUser()+" activated productions\nhe obtained following resources: "+update.getReceivedResources());
    }


    @Override
    public void manageUserInteraction() {
        boolean doneSelection = false;
        do {
            selectProductions();
            if(areValidRequestedProductions()){
                resourcesChoice();
                if(checkRequiredResources()) {
                    messageToSend.setHowToTakeResources(cli.askInstructionsOnHowToTakeResources(calculateInputResources()));
                    doneSelection = true;
                }
                else System.out.println("You cannot activate chosen production because you don't have enough resources");
            }
            else System.out.println("Selected productions are not available, try again");
        } while(!doneSelection);
        client.sendMessage(messageToSend);
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
        if(countExtraProductionEffect() == 1) {
            System.out.printf("One extra production of type %s available, do you want to activate it? (yes/no) ", getExtraProductionType(0));
            answer = input.nextLine();
            requiredProduction.setExtraSlot1(answer.equalsIgnoreCase("yes"));
            requiredProduction.setExtraSlot2(false);
        }
        if(countExtraProductionEffect() == 2) {
            System.out.printf("Two extra productions available, one of type: %s, do you want to activate it? (yes/no) ", getExtraProductionType(0));
            answer = input.nextLine();
            requiredProduction.setExtraSlot1(answer.equalsIgnoreCase("yes"));
            System.out.printf("Second is of type: %s, do you want to activate it? (yes/no) ", getExtraProductionType(1));
            answer = input.nextLine();
            requiredProduction.setExtraSlot2(answer.equalsIgnoreCase("yes"));
        }
    }


    private void resourcesChoice() {
        if(requiredProduction.isBasic()) {
            System.out.println("You chose basic production power, please select two input resources " +
                    "and one output resource [Coin (C), Servant(SE), Shield(SH), Stone(ST)]");
            messageToSend.setInput1(askValidResource());
            messageToSend.setInput2(askValidResource());
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

    private ResourceType askValidResource() {
        boolean done = false;
        ResourceType resource = null;
        while(!done) {
            answer = input.nextLine();
            resource = cli.fromStringToResourceType(answer);
            if(resource!=null){
                done = true;
            }
        }
        return resource;
    }






}
