package it.polimi.ingsw.client;

import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.network.RemoteView;
import it.polimi.ingsw.utils.messages.serverMessages.Errors.ErrorMessage;
import it.polimi.ingsw.utils.messages.serverMessages.ServerMessage;

import java.util.logging.Level;

public class OfflineRemoteView extends RemoteView {

    private final OfflineClient client;

    protected OfflineRemoteView(User user, GameController gameController, OfflineClient client) {
        super(user, gameController);
        this.client = client;
    }

    @Override
    public void sendError(ErrorMessage errorMessage) {
        errorMessage.handleMessage(client);
    }

    @Override
    public void update(ServerMessage message) {
        LOGGER.log(Level.INFO, "Message received of type "+message.getClass().getName());
        message.handleMessage(client);
    }
}
