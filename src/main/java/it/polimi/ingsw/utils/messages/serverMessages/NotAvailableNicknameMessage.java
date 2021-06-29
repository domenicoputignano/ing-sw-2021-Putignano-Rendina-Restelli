package it.polimi.ingsw.utils.messages.serverMessages;

import it.polimi.ingsw.client.CliStatesController;
import it.polimi.ingsw.network.Client;

/**
 * Class representing a message sent from server in order to notify a guest that he has just chosen
 * a not available nickname, so the selection has to be done again.
 */
public class NotAvailableNicknameMessage implements ServerMessage {

    /**
     * Method called by client in order to show message itself.
     * @param handler {@link Client} instance that manages the update itself.
     */
    @Override
    public void handleMessage(Client handler) {
        handler.getUI().render(this);
        CliStatesController.updateCliState(new ServerAsksForNickname(), handler.getUI());
    }

}
