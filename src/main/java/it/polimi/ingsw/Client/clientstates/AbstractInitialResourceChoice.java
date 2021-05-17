package it.polimi.ingsw.Client.clientstates;

import it.polimi.ingsw.Client.clientstates.cli.InitialResourceChoiceCLI;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ClientMessages.ResourceChoiceMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.InitialResourceChoiceUpdate;

public abstract class AbstractInitialResourceChoice extends AbstractClientState {

    protected ResourceChoiceMessage messageToSend;
    public AbstractInitialResourceChoice(Client client) {
        super(client);
    }

    @Override //TODO
    public AbstractClientState getGUIVersion() {
        return null;
    }

    @Override
    public InitialResourceChoiceCLI getCLIVersion() {
        return new InitialResourceChoiceCLI(client);
    }
}
