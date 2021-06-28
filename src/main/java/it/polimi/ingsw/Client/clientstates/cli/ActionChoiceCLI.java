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
        synchronized (WaitForTurnCLI.occupied) {
            boolean choiceOK;
            do {
                choiceOK = actionChoice();
            } while (!choiceOK);
            cli.manageUserInteraction();
        }
    }

    private boolean actionChoice() {
        System.out.println("Here is your personal board");
        cli.printPersonalBoard(client.getGame().getPlayer(client.getUser()).getPersonalBoard());
            //Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Sono nella action choice e sto leggendo da input");
            if(normalActionAlreadyDone()) {
                System.out.println("Choose between Leader Action (L), Move Resources (M) and End Turn (E) ");
            } else {
                System.out.println("Choose between Activate Production (A)," +
                        " Buy (B), Take Resources (T), Leader Action (L), Move Resources (M) " +
                        " ");
            }
            if(!client.getGame().isSoloMode()) {
                System.out.println("You can type (PB) to see other players' board, (MARKET) to see market tray or (DECKS) to see development cards");
            }
            String choice = input.next().toUpperCase();
            switch(choice) {
                case "A" : {
                    if(normalActionAlreadyDone()) {
                        return normalActionAlreadyDoneMessage();
                    } else {
                        cli.changeCliState(new ActivateProductionCLI(client));
                        return true;
                    }
                }
                case "B" : {
                    if(normalActionAlreadyDone()) return normalActionAlreadyDoneMessage();
                    else {
                        cli.changeCliState(new BuyDevCardCLI(client));
                        return true;
                    }
                }
                case "T" : {
                    if(normalActionAlreadyDone()) return normalActionAlreadyDoneMessage();
                    else {
                        cli.changeCliState(new TakeResourcesFromMarketCLI(client));
                        return true;
                    }
                }
                case "L" : {
                    cli.changeCliState(new LeaderActionCLI(client));
                    return true;
                }
                case "M" : {
                    cli.changeCliState(new MoveResourcesCLI(client));
                    return true;
                }
                case "E" : {
                    if(!normalActionAlreadyDone()) {
                        System.out.println("You can't end you turn now, you have to do a normal action before");
                        return false;
                    } else {
                        endTurn();
                        cli.changeCliState(new WaitForTurnCLI(client));
                        return true;
                    }
                }
                case "MARKET" : {
                    cli.showMarketTray();
                    return false;
                }
                case "DECKS" : {
                    cli.printDecks();
                    return false;
                }
                case "PB" : {
                    cli.askAndShowPlayerBoard();
                    return false;
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
