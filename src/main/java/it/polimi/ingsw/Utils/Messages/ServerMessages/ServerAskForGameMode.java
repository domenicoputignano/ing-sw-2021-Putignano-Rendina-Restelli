package it.polimi.ingsw.Utils.Messages.ServerMessages;

import it.polimi.ingsw.Client.CliStatesController;
import it.polimi.ingsw.Network.Client;

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
