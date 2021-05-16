package it.polimi.ingsw.Client.clientstates.cli;

import it.polimi.ingsw.Client.clientstates.AbstractInitialResourceChoice;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;

public class InitialResourceChoiceCLI extends AbstractInitialResourceChoice {

    public InitialResourceChoiceCLI(Client client) {
        super(client);
    }

    @Override
    public void render(ServerMessage message) {

    }

    @Override
    public void manageUserInteraction() {

    }
}
