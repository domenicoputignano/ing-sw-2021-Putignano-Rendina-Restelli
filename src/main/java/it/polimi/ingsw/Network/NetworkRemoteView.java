package it.polimi.ingsw.Network;


import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Errors.ErrorMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;

import java.util.List;

public class NetworkRemoteView extends RemoteView {

    private ClientStatus clientStatus;

    public NetworkRemoteView(User user, GameController gameController, ClientStatus clientStatus) {
        super(user, gameController);
        this.clientStatus = clientStatus;
    }

    public NetworkRemoteView(RemoteView oldRemoteView, ClientStatus clientStatus) {
        super(oldRemoteView.user, oldRemoteView.gameController);
        this.user.setActive(true);
        this.clientStatus = clientStatus;
        /*this.user = oldRemoteView.user;
        this.gameController = oldRemoteView.gameController;
        this.game = gameController.getModel();
        game.addObserver(this);*/
    }

    public void sendError(ErrorMessage errorMessage) {
        clientStatus.send(errorMessage);
    }

    public void handlePlayerDisconnection() {
        user.setActive(false);
        game.removeObserver(this);
        gameController.handlePlayerDisconnection(this.user, this);
    }

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

    public void update(ServerMessage message) {
        clientStatus.send(message);
    }
}
