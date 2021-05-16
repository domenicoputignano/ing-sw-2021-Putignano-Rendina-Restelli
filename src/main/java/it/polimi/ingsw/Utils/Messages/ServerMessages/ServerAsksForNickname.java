package it.polimi.ingsw.Utils.Messages.ServerMessages;


import it.polimi.ingsw.Client.ClientStatesController;
import it.polimi.ingsw.Network.Client;

public class ServerAsksForNickname implements ServerMessage {

    @Override
    public void handleMessage(Client handler) {
        ClientStatesController.updateClientState(this, handler.getUI());
    }



}
