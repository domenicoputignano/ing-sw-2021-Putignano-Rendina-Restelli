package it.polimi.ingsw.utils.messages.serverMessages;


import it.polimi.ingsw.client.CliStatesController;
import it.polimi.ingsw.client.reducedmodel.ReducedGame;
import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.network.Client;

/**
 * Class that collects in a message all the information needed by a client that is reconnecting in order
 * to let him resuming the game.
 */
public class GameResumedMessage extends GameSetupMessage {
    /**
     * Instance user related to reconnecting player
     */
    private final User savedUserInstance;
    /**
     * Instance of user in turn.
     */
    private final User currentUser;

    public GameResumedMessage(ReducedGame game, User savedUserInstance, User playerInTurn) {
        super(game);
        this.savedUserInstance = savedUserInstance;
        this.currentUser = playerInTurn;
    }

    /**
     * Method called by client in order to initialize and show its reduced model.
     * @param handler {@link Client} instance that manages the update itself.
     */
    public void handleMessage(Client handler) {
        handler.setupGame(this);
        handler.getUI().render(this);
        CliStatesController.updateCliState(this, handler.getUI());
    }

    public User getSavedUserInstance() {
        return savedUserInstance;
    }

    public User getCurrentUser() { return currentUser; }
}
