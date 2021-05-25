package it.polimi.ingsw.Client.clientstates.cli;

import it.polimi.ingsw.Client.clientstates.AbstractWaitForGameSetup;
import it.polimi.ingsw.Client.view.CLI;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;

public class WaitForGameSetupCLI extends AbstractWaitForGameSetup {

    private final CLI cli;

    public WaitForGameSetupCLI(Client client) {
        super(client);
        cli = (CLI) client.getUI();
    }

    @Override
    public void manageUserInteraction() {
        System.out.println("Game will start in a while...");
    }
}
