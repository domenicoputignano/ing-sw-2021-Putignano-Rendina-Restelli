package it.polimi.ingsw.utils.messages.serverMessages;


import it.polimi.ingsw.client.CliStatesController;
import it.polimi.ingsw.network.Client;

/**
 * Class representing the first message exchanged by server and connecting client, in order to let him choose
 * its nickname. This is a crucial step because after its decision, server will establish if it is trying to connect
 * to a preexisting instance of a game or it is a new guest.
 */
public class ServerAsksForNickname implements ServerMessage {

    /**
     * Method called by client in order to show message itself and to show the first step of the configuration.
     * @param handler {@link Client} instance that manages the update itself.
     */
    @Override
    public void handleMessage(Client handler) {
        handler.getUI().render(this);
        CliStatesController.updateCliState(this, handler.getUI());
    }

}
