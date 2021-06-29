package it.polimi.ingsw.utils.messages.serverMessages;

import it.polimi.ingsw.network.Client;

import java.io.Serializable;

/**
 * Interface that encapsulates all messages sent by server to clients. It implements {@link Serializable} in order
 * to allow message exchange through the network.
 */
public interface ServerMessage extends Serializable {

    /**
     * Method that establishes how the client will manage message itself.
     * @param handler Client instance that has to handle the message.
     */
    public void handleMessage(Client handler);

}
