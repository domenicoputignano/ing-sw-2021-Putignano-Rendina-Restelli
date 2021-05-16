package it.polimi.ingsw.Client.clientstates;

import it.polimi.ingsw.Client.clientstates.cli.NumOfPlayersChoiceCLI;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ClientMessages.NumOfPlayerChoiceMessage;

public abstract class AbstractNumOfPlayersChoice extends AbstractClientState {

    protected NumOfPlayerChoiceMessage messageToSend;

    protected AbstractNumOfPlayersChoice(Client client) {
        super(client);
    }


    @Override
    public AbstractClientState getGUIVersion() {
        return null;
    }

    @Override
    public AbstractClientState getCLIVersion() {
        return new NumOfPlayersChoiceCLI(client);
    }
}
