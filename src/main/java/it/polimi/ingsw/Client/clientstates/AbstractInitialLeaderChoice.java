package it.polimi.ingsw.Client.clientstates;

import it.polimi.ingsw.Client.clientstates.cli.InitialLeaderChoiceCLI;
import it.polimi.ingsw.Network.Client;

public abstract class AbstractInitialLeaderChoice extends AbstractClientState {

    public AbstractInitialLeaderChoice(Client client) {
        super(client);
    }

    @Override //TODO
    public AbstractClientState getGUIVersion() {
        return null;
    }

    @Override
    public InitialLeaderChoiceCLI getCLIVersion() {
        return new InitialLeaderChoiceCLI(client);
    }
}
