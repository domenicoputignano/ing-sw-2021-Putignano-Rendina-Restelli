package it.polimi.ingsw.Utils.Messages.ClientMessages;

import it.polimi.ingsw.Network.ClientStatus;

import java.io.IOException;

public interface ConfigurationMessage extends ClientMessage {

    void handleConfigurationMessage(ClientStatus clientStatus) throws IOException, ClassNotFoundException;


}
