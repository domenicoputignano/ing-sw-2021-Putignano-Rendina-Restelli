package it.polimi.ingsw.Client.clientstates.cli;

import it.polimi.ingsw.Client.clientstates.AbstractActionChoice;
import it.polimi.ingsw.Client.view.CLI;
import it.polimi.ingsw.Network.Client;

import java.util.Scanner;

public class ActionChoiceCLI extends AbstractActionChoice {

    private final Scanner input = new Scanner(System.in);
    private final CLI cli;


    public ActionChoiceCLI(Client client) {
        super(client);
        cli = (CLI) client.getUI();
    }

    public void manageUserInteraction() {
        boolean choiceOK;
        do {
            choiceOK = actionChoice();
        } while(!choiceOK);
        cli.manageUserInteraction();
    }

    private boolean actionChoice() {
        if(normalActionAlreadyDone()) {
            System.out.println("Choose between Leader Action (L), Move Resources (M) and End Turn (E) ");
        } else {
            System.out.print("Choose between Activate Production (A)," +
                    " Buy (B), Take Resources (T), Leader Action (L) and Move Resources (M) ");
        }
        String choice = input.next().toUpperCase();
        switch(choice) {
            case "A" : {
                if(normalActionAlreadyDone()) {
                    return normalActionAlreadyDoneMessage();
                } else {
                    cli.changeClientState(new ActivateProductionCLI(client));
                    return true;
                }
            }
            case "B" : {
                if(normalActionAlreadyDone()) return normalActionAlreadyDoneMessage();
                else {
                    cli.changeClientState(new BuyDevCardCLI(client));
                    return true;
                }
            }
            case "T" : {
                if(normalActionAlreadyDone()) return normalActionAlreadyDoneMessage();
                else {
                    cli.changeClientState(new TakeResourcesFromMarketCLI(client));
                    return true;
                }
            }
            case "L" : {
                cli.changeClientState(new LeaderActionCLI(client));
                return true;
            }
            case "M" : {
                cli.changeClientState(new MoveResourcesCLI(client));
                return true;
            }
            case "E" : {
                if(!normalActionAlreadyDone()) {
                    System.out.println("You can't end you turn now, you have to do a normal action before");
                    return false;
                } else {
                    endTurn();
                    cli.changeClientState(new WaitForTurnCLI(client));
                    return true;
                }
            }
            default: {
                System.out.println("Invalid choice, try again");
                return false;
            }
        }
    }

    private boolean normalActionAlreadyDoneMessage(){
        System.out.println("You have already done the normal action for this turn, please choose another action");
        return false;
    }
}
