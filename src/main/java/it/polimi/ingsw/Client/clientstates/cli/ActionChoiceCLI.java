package it.polimi.ingsw.Client.clientstates.cli;

import it.polimi.ingsw.Client.clientstates.AbstractActionChoice;
import it.polimi.ingsw.Client.view.CLI;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;

import java.util.Scanner;

public class ActionChoiceCLI extends AbstractActionChoice {

    private Scanner input = new Scanner(System.in);
    private final CLI cli;


    public ActionChoiceCLI(Client client) {
        super(client);
        cli = (CLI) client.getUI();
    }

    @Override
    public void render(ServerMessage message) {
        System.out.println("It's now your turn ");
    }

    public void manageUserInteraction() {
        System.out.print("Choose between Activate Production (A)," +
                " Buy (B), Take Resources (T), Leader Action (L) and Move Resources (M) ");
        String choice = input.next();
        switch(choice) {
            case "A" : {
                (new ActivateProductionCLI(client)).manageUserInteraction();
                break;
            }
            case "B" : {
                (new BuyDevCardCLI(client)).manageUserInteraction();
                break;
            }
            case "T" : {
                (new TakeResourcesFromMarketCLI(client)).manageUserInteraction();
                break;
            }
            case "L" : {
                (new LeaderActionCLI(client)).manageUserInteraction();
                break;
            }
            case "M" : {
                cli.changeClientState(new MoveResourcesCLI(client));
                cli.manageUserInteraction();
                break;
            }
            //TODO inserito solo per non dimenticarci
            default: break;
        }
    }
}
