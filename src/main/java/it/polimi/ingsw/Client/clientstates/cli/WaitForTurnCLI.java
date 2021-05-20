package it.polimi.ingsw.Client.clientstates.cli;

import it.polimi.ingsw.Client.clientstates.AbstractClientState;
import it.polimi.ingsw.Client.clientstates.AbstractWaitForTurn;
import it.polimi.ingsw.Client.view.CLI;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;

import java.util.Scanner;

public class WaitForTurnCLI extends AbstractWaitForTurn {

    private final CLI cli;
    private Scanner input = new Scanner(System.in);

    public WaitForTurnCLI(Client client) {
        super(client);
        this.cli = (CLI) client.getUI();
    }

    @Override
    public AbstractClientState getGUIVersion() {
        return null;
    }

    @Override
    public AbstractClientState getCLIVersion() {
        return null;
    }

    @Override
    public void render(ServerMessage message) {

    }

    @Override
    public void manageUserInteraction() {
        while(!inTurn) {
            String input = this.input.next();
            if(!input.isEmpty()) System.out.println("It's not your turn, please wait..");
        }
    }
}
