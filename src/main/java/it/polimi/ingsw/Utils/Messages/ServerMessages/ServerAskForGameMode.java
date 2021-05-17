package it.polimi.ingsw.Utils.Messages.ServerMessages;

import it.polimi.ingsw.Client.ClientStatesController;
import it.polimi.ingsw.Network.Client;

public class ServerAskForGameMode implements ServerMessage {

    @Override
    public void handleMessage(Client handler) {
        handler.getUI().render(this);
        ClientStatesController.updateClientState(this, handler.getUI());
    }
}