package it.polimi.ingsw.utils.messages.clientMessages;

import it.polimi.ingsw.network.ClientStatus;

import java.io.IOException;

public interface ConfigurationMessage extends ClientMessage {

    void handleConfigurationMessage(ClientStatus clientStatus) throws IOException, ClassNotFoundException;


}
