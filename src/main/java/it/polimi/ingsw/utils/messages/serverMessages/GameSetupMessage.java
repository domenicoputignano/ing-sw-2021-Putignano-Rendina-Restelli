package it.polimi.ingsw.utils.messages.serverMessages;


import it.polimi.ingsw.client.CliStatesController;
import it.polimi.ingsw.client.reducedmodel.ReducedGame;
import it.polimi.ingsw.network.Client;

/**
 * Class representing first message sent to each player after a game creation.
 */
public class GameSetupMessage implements ServerMessage {
    /**
     * Instance of game that will make up client reduced model.
     */
    private final ReducedGame game;

    public GameSetupMessage(ReducedGame game) {
        this.game = game;
    }

    public ReducedGame getGame() {
        return game;
    }

    /**
     * Method called by client in order to initialize and show its reduced model.
     * @param handler {@link Client} instance that manages the update itself.
     */
    @Override
    public void handleMessage(Client handler) {
        handler.setupGame(this);
        handler.getUI().render(this);
        CliStatesController.updateCliState(this, handler.getUI());
    }
}
