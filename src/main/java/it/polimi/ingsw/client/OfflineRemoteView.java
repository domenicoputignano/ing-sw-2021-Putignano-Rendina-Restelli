package it.polimi.ingsw.client;

import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.network.RemoteView;
import it.polimi.ingsw.utils.messages.serverMessages.Errors.ErrorMessage;
import it.polimi.ingsw.utils.messages.serverMessages.ServerMessage;

import java.util.logging.Level;

/**
 * Class that inherits from {@link RemoteView} and implements the offline version of that class.
 * This class receives {@link it.polimi.ingsw.utils.messages.serverMessages.ServerMessage} from the {@link it.polimi.ingsw.model.Game}
 * and forwards them to the {@link OfflineClient} which handles them.
 */
public class OfflineRemoteView extends RemoteView {

    /**
     * The client which handles the messages sent from this class.
     */
    private final OfflineClient client;

    /**
     * Constructs an instance of this class from the given parameters.
     * @param user the user owner of this remote view.
     * @param gameController the game controller which handles the messages sent from the client.
     * @param client the client associated to this remote view.
     */
    protected OfflineRemoteView(User user, GameController gameController, OfflineClient client) {
        super(user, gameController);
        this.client = client;
    }

    /**
     * Sends an error to the client which handles it.
     * @param errorMessage message containing error details.
     */
    @Override
    public void sendError(ErrorMessage errorMessage) {
        errorMessage.handleMessage(client);
    }

    /**
     * Sends an update by the model to the {@link OfflineClient} which handles it.
     * @param message message containing change details.
     */
    @Override
    public void update(ServerMessage message) {
        message.handleMessage(client);
    }
}
