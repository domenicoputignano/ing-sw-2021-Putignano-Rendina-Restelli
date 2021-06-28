package it.polimi.ingsw.network;

import it.polimi.ingsw.client.reducedmodel.ReducedGame;
import it.polimi.ingsw.client.view.UI;
import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.utils.messages.clientMessages.ClientMessage;
import it.polimi.ingsw.utils.messages.serverMessages.GameResumedMessage;
import it.polimi.ingsw.utils.messages.serverMessages.GameSetupMessage;


import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Since the project supports the possibility of playing an offline solo mode match, this abstract class
 * has methods and attributes that a {@link NetworkClient} and {@link it.polimi.ingsw.client.OfflineClient} has in common to run
 * and display game.
 */
public abstract class Client {

    protected final Logger LOGGER = Logger.getLogger(Client.class.getName());
    /**
     * Reduced game instance deployed client side.
     */
    protected ReducedGame game;
    /**
     * User bound to Client entity
     */
    protected User user;
    /**
     * User interface reference
     */
    protected UI ui;

    /**
     * Abstract method that states how the client will start.
     * @param startAsGui variable that establishes if user wants to play with GUI
     */
    public abstract void start(boolean startAsGui);


    public abstract void sendMessage(ClientMessage message);


    /**
     * This initializes game instance.
     */
    public void setupGame(GameSetupMessage message) {
        game = message.getGame();
    }

    /**
     * It makes a game setup after a client reconnection.
     * @param message message containing information about the game.
     */
    public void setupGame(GameResumedMessage message) {
            if(this.getUser()==null) {
                LOGGER.log(Level.INFO, "Sono il client che si è riconnesso");
                bindUser(message.getSavedUserInstance().getNickname());
                game = message.getGame();
                ui.setSoloMode(game.isSoloMode());
            } else {
                LOGGER.log(Level.INFO, "Sono il client che stava già giocando");
            }
    }

    /**
     * It basically sets user inside the client.
     */
    public void bindUser(String nickname) {
        user = new User(nickname);
    }

    /**
     * Returns position associated to user instance of the client.
     */
    public int getUserPosition() { return game.getPlayer(user).getPosition(); }

    public User getUser() { return user; }

    public UI getUI() {
        return ui;
    }

    public ReducedGame getGame() {
        return game;
    }

}

