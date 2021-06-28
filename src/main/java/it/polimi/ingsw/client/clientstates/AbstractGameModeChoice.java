package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.clientMessages.GameModeChoiceMessage;

public abstract class AbstractGameModeChoice extends AbstractClientState {

    protected GameModeChoiceMessage messageToSend;

    public AbstractGameModeChoice(Client client) {
        super(client);
    }

}
