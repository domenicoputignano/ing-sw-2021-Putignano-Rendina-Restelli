package it.polimi.ingsw.utils.messages.serverMessages;

import it.polimi.ingsw.client.CliStatesController;
import it.polimi.ingsw.network.Client;

public class ServerAskForGameMode implements ServerMessage {

    private final String givenNickname;

    public ServerAskForGameMode(String givenNickname) {
        this.givenNickname = givenNickname;
    }

    @Override
    public void handleMessage(Client handler) {
        handler.bindUser(givenNickname);
        handler.getUI().render(this);
        CliStatesController.updateCliState(this, handler.getUI());
    }
}
