package it.polimi.ingsw.Client.clientstates.cli;

import it.polimi.ingsw.Client.clientstates.AbstractActionChoice;
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
                ActivateProductionCLI action = new ActivateProductionCLI();

            }
            case "B" : {
                (new BuyDevCardCLI()).manageUserInteraction();
            }
            case "T" : {

            }
            case "L" : {

            }
            case "M" : {

            }
        }
    }
}
