package it.polimi.ingsw.Client.clientstates;

import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ClientMessages.NumOfPlayerChoiceMessage;

public abstract class AbstractNumOfPlayersChoice extends AbstractClientState {

    protected NumOfPlayerChoiceMessage messageToSend;

    protected AbstractNumOfPlayersChoice(Client client) {
        super(client);
    }

}
