package it.polimi.ingsw.utils.messages.serverMessages;

import it.polimi.ingsw.network.Client;

/**
 * Class representing a message sent from server executors
 * in order to reply to a {@link it.polimi.ingsw.utils.messages.clientMessages.PingMessage} sent before by client.
 */
public class PongMessage implements ServerMessage {
    @Override
    public void handleMessage(Client handler) { }
}
