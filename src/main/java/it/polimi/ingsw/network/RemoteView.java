package it.polimi.ingsw.network;

import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.Observer;
import it.polimi.ingsw.utils.messages.clientMessages.GameControllerHandleable;
import it.polimi.ingsw.utils.messages.serverMessages.Errors.ErrorMessage;
import it.polimi.ingsw.utils.messages.serverMessages.ServerMessage;

import java.util.logging.Logger;

/**
 * This abstract class represents View component in MVC architectural pattern, it is bound to a specific user.
 * It is a {@link Game} observer and gets notified whenever model changes.
 * It also as a reference to a {@link GameController} instance, in this way it forwards message handling to it.
 */
public abstract class RemoteView implements Observer<ServerMessage> {

    protected Logger LOGGER = Logger.getLogger(RemoteView.class.getName());
    protected User user;
    protected Game game;
    protected GameController gameController;


    /**
     * Constructor of the class. It initializes references to game, user and game controller.
     */
    protected RemoteView(User user, GameController gameController) {
        this.user = user;
        this.gameController = gameController;
        this.game = gameController.getModel();
        game.addObserver(this);
    }

    /**
     * Abstract method that notifies user that an error occurred.
     * @param errorMessage message containing error details.
     */
    public abstract void sendError(ErrorMessage errorMessage);

    /**
     * Abstract method that notifies user of a model change.
     * @param message message containing change details.
     */
    public abstract void update(ServerMessage message);

    /**
     * Forwards message handling to game controller.
     * @param message message that will be handled by game controller.
     */
    public void handleClientMessage(GameControllerHandleable message) {  message.handleMessage(gameController, this); }

    /**
     * Returns player instance given {@link User} of the remote view.
     */
    public Player getPlayer() {
        return game.getPlayer(user);
    }

    public Game getModel() { return game; }

}
