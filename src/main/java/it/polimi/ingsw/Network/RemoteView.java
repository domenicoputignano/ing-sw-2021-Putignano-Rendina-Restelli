package it.polimi.ingsw.Network;

import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Observer;
import it.polimi.ingsw.Utils.Messages.ClientMessages.ClientMessage;
import it.polimi.ingsw.Utils.Messages.ClientMessages.GameControllerHandleable;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Errors.ErrorMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;

import java.util.logging.Logger;

public abstract class RemoteView implements Observer<ServerMessage> {

    protected final Logger LOGGER = Logger.getLogger(RemoteView.class.getName());
    protected final User user;
    protected final Game game;
    protected final GameController gameController;


    protected RemoteView(User user, GameController gameController) {
        this.user = user;
        this.gameController = gameController;
        this.game = gameController.getModel();
        game.addObserver(this);
    }

    public abstract void sendError(ErrorMessage errorMessage);
    public abstract void update(ServerMessage message);

    public void handleClientMessage(GameControllerHandleable message) {  message.handleMessage(gameController, this); }

    public Player getPlayer() {
        return game.getPlayer(user);
    }

    public Game getModel() { return game; }

}
