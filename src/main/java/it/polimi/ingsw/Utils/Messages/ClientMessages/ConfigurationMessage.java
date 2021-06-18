package it.polimi.ingsw.Utils.Messages.ClientMessages;

import it.polimi.ingsw.Network.ClientSetupConnection;

import java.io.IOException;

public interface ConfigurationMessage extends ClientMessage {

    void handleConfigurationMessage(ClientSetupConnection connection) throws IOException, ClassNotFoundException;


}
