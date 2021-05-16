package it.polimi.ingsw.Client.clientstates;

import it.polimi.ingsw.Client.clientstates.cli.WaitForGameSetupCLI;
import it.polimi.ingsw.Network.Client;

public abstract class AbstractWaitForGameSetup extends AbstractClientState {

    protected AbstractWaitForGameSetup(Client client) {
        super(client);
    }

    @Override //TODO
    public AbstractClientState getGUIVersion() {
        return null;
    }

    @Override
    public WaitForGameSetupCLI getCLIVersion() {
        return new WaitForGameSetupCLI(client);
    }
}
