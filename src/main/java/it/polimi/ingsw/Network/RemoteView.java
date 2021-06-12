package it.polimi.ingsw.Network;

import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Observer;
import it.polimi.ingsw.Utils.Messages.ClientMessages.ClientMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Errors.ErrorMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;

import java.util.logging.Logger;

public class RemoteView implements Observer<ServerMessage> {

    private final Logger LOGGER = Logger.getLogger(RemoteView.class.getName());
    private final User user;
    private final Game game;
    private final GameController gameController;
    private final ClientStatus clientStatus;

    public RemoteView(User user, GameController gameController, ClientStatus clientStatus) {
        this.user = user;
        this.gameController = gameController;
        this.game = gameController.getModel();
        this.clientStatus = clientStatus;
        game.addObserver(this);
    }

    public RemoteView(RemoteView oldRemoteView, ClientStatus clientStatus) {
        this.user = oldRemoteView.user;
        this.user.setActive(true);
        this.gameController = oldRemoteView.gameController;
        this.game = gameController.getModel();
        this.clientStatus = clientStatus;
        game.addObserver(this);
    }


    public void sendError(ErrorMessage errorMessage) {
        clientStatus.send(errorMessage);
     }

    public void handleClientMessage(ClientMessage message) {  message.handleMessage(gameController, this); }

    public void handlePlayerDisconnection() {
        user.setActive(false);
        game.nextTurn();
    }

    public void update(ServerMessage message) {
        clientStatus.send(message);
    }

    public Player getPlayer() {
        return game.getPlayer(user);
    }

    public Game getModel() { return game; }

}
