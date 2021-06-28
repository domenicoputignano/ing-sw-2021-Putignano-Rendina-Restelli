package it.polimi.ingsw.Utils.Messages.ServerMessages;


import it.polimi.ingsw.Client.CliStatesController;
import it.polimi.ingsw.Network.Client;

public class ServerAskForNumOfPlayer implements ServerMessage {

    @Override
    public void handleMessage(Client handler) {
        handler.getUI().render(this);
        CliStatesController.updateCliState(this, handler.getUI());
    }
}
