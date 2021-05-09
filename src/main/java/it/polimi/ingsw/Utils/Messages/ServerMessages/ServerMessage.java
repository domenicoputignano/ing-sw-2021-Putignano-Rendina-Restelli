package it.polimi.ingsw.Utils.Messages.ServerMessages;

import it.polimi.ingsw.Network.Client;

import java.io.Serializable;

public interface ServerMessage extends Serializable {

    public void handleMessage(Client handler);

}
