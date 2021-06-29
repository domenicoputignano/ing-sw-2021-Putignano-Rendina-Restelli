package it.polimi.ingsw.utils.messages.serverMessages;

import it.polimi.ingsw.client.CliStatesController;
import it.polimi.ingsw.network.Client;

/**
 * Class representing a message through server asks the guest which kind of mode he wants to play.
 */
public class ServerAskForGameMode implements ServerMessage {

    /**
     * Nickname selected by guest
     * with the reply to {@link ServerAsksForNickname}.
     */
    private final String givenNickname;

    public ServerAskForGameMode(String givenNickname) {
        this.givenNickname = givenNickname;
    }

    /**
     * Method called by client in order to update user bound to it and to show next step of the configuration.
     * @param handler {@link Client} instance that manages the update itself.
     */
    @Override
    public void handleMessage(Client handler) {
        handler.bindUser(givenNickname);
        handler.getUI().render(this);
        CliStatesController.updateCliState(this, handler.getUI());
    }
}
