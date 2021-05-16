package it.polimi.ingsw.Client.clientstates;

import it.polimi.ingsw.Client.clientstates.cli.GameModeChoiceCLI;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ClientMessages.UsernameChoiceMessage;

public abstract class AbstractGameModeChoice extends AbstractClientState {

    public AbstractGameModeChoice(Client client) {
        super(client);
    }

    @Override
    public AbstractClientState getCLIVersion() {
        return new GameModeChoiceCLI(client);
    }

    @Override //TODO
    public AbstractClientState getGUIVersion() {
        return null;
    }
}
