package it.polimi.ingsw.utils.messages.serverMessages;

import it.polimi.ingsw.network.Client;

import java.io.Serializable;

public interface ServerMessage extends Serializable {

    public void handleMessage(Client handler);

}
