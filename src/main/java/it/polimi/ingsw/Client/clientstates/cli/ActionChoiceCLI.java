package it.polimi.ingsw.Client.clientstates.cli;

import it.polimi.ingsw.Client.clientstates.AbstractActionChoice;
import it.polimi.ingsw.Client.view.CLI;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;

import java.util.Scanner;

public class ActionChoiceCLI extends AbstractActionChoice {
    @Override
    public void render(ServerMessage message) {
        System.out.println("It's now your turn: ");
    }

    public void manageUserInteraction() {
        System.out.print("Choose between: Activate Production (A)," +
                " Buy (B), Take Resources (T), Leader Action (L) and Move Resources (M)");
        Scanner input = new Scanner(System.in);
        String choice = input.next();
        switch(choice) {
            case "A" : {
                (new ActivateProductionCLI((CLI) client.getUI())).manageUserInteraction();

            }
            case "B" : {
                (new BuyDevCardCLI((CLI) client.getUI())).manageUserInteraction();
            }
            case "T" : {

            }
            case "L" : {
                (new LeaderActionCLI((CLI)client.getUI())).manageUserInteraction();
            }
            case "M" : {

            }
            //TODO inserito solo per non dimenticarci
            default: break;
        }
    }
}
