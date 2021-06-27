package it.polimi.ingsw.Network;


import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Errors.ErrorMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;

import java.util.List;

/**
 * Class that inherits from {@link RemoteView} and has also a reference to a {@link ClientStatus}
 * that allows messages receiving and sending, because Server side it has to communicate with remote host.
 */
public class NetworkRemoteView extends RemoteView {

    private ClientStatus clientStatus;

    /**
     * Standard constructor used server side at the moment of game creation.
     */
    public NetworkRemoteView(User user, GameController gameController, ClientStatus clientStatus) {
        super(user, gameController);
        this.clientStatus = clientStatus;
    }

    /**
     * This constructor is used when an user reconnects to an already existing game.
     * It simply, copies references from previous {@link RemoteView} instance and binds
     * new instance of a {@link ClientStatus}.
     * @param oldRemoteView pre-existing remote view with info about
     * @param clientStatus network channel used to send messages to remote host.
     */
    public NetworkRemoteView(RemoteView oldRemoteView, ClientStatus clientStatus) {
        super(oldRemoteView.user, oldRemoteView.gameController);
        this.user.setActive(true);
        this.clientStatus = clientStatus;
    }

    /**
     * Notifies a remote host that an error occurred.
     * @param errorMessage message containing error details.
     */
    public void sendError(ErrorMessage errorMessage) {
        clientStatus.send(errorMessage);
    }

    /**
     * Handles a player disconnection by removing itself as a model observer and notifying game
     * controller.
     */
    public void handlePlayerDisconnection() {
        user.setActive(false);
        game.removeObserver(this);
        gameController.handlePlayerDisconnection(this.user, this);
    }

    /**
     * It alerts game controller that a client is attempting to reconnect.
     * @param reconnectingUser user that is reconnecting.
     */
    public void handlePlayerReconnection(User reconnectingUser) {
        gameController.handlePlayerReconnection(reconnectingUser);
    }

    public void deleteMatch(List<User> userInAPausedMatch) {
        clientStatus.deleteMatch(userInAPausedMatch);
        this.game = null;
        this.gameController = null;
        this.clientStatus = null;
        this.user = null;
    }

    /**
     * Notifies a remote host that a change occurred.
     * @param message message containing change details.
     */
    public void update(ServerMessage message) {
        clientStatus.send(message);
    }
}
