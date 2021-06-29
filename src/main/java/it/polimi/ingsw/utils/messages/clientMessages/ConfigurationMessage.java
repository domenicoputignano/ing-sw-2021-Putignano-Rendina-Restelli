package it.polimi.ingsw.utils.messages.clientMessages;

import it.polimi.ingsw.network.ClientStatus;

import java.io.IOException;

/**
 * Interface that encapsulates all messages that allows client to configure a game before its beginning.
 */
public interface ConfigurationMessage extends ClientMessage {

    /**
     * Calls the right method of {@link ClientStatus} in order to process the message itself.
     * @param clientStatus {@link ClientStatus} instance that will process message itself.
     */
    void handleConfigurationMessage(ClientStatus clientStatus) throws IOException, ClassNotFoundException;

}
