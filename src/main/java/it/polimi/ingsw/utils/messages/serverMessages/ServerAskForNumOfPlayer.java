package it.polimi.ingsw.utils.messages.serverMessages;


import it.polimi.ingsw.client.CliStatesController;
import it.polimi.ingsw.network.Client;

public class ServerAskForNumOfPlayer implements ServerMessage {

    @Override
    public void handleMessage(Client handler) {
        handler.getUI().render(this);
        CliStatesController.updateCliState(this, handler.getUI());
    }
}
