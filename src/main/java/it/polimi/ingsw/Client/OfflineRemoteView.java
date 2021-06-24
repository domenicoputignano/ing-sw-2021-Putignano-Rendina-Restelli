package it.polimi.ingsw.Client;

import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.RemoteView;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Errors.ErrorMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;

import java.util.concurrent.ExecutorService;
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
