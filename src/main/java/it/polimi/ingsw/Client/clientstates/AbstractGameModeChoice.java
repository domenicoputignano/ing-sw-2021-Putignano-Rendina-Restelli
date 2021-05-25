package it.polimi.ingsw.Client.clientstates;

import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ClientMessages.GameModeChoiceMessage;

public abstract class AbstractGameModeChoice extends AbstractClientState {

    protected GameModeChoiceMessage messageToSend;

    public AbstractGameModeChoice(Client client) {
        super(client);
    }

}
