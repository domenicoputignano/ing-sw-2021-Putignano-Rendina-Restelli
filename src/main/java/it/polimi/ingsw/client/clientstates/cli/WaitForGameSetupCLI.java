package it.polimi.ingsw.client.clientstates.cli;

import it.polimi.ingsw.client.clientstates.AbstractWaitForGameSetup;
import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.network.Client;

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
