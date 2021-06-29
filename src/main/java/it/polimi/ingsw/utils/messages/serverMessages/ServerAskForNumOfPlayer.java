package it.polimi.ingsw.utils.messages.serverMessages;


import it.polimi.ingsw.client.CliStatesController;
import it.polimi.ingsw.network.Client;

/**
 * Class representing a message sent from server in order to ask the guest the number of players
 * he wants in a game.
 */
public class ServerAskForNumOfPlayer implements ServerMessage {

    /**
     * Method called by client in order to show message itself and to show next step of the configuration.
     * @param handler {@link Client} instance that manages the update itself.
     */
    @Override
    public void handleMessage(Client handler) {
        handler.getUI().render(this);
        CliStatesController.updateCliState(this, handler.getUI());
    }
}
