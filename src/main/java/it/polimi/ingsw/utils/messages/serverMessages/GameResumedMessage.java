package it.polimi.ingsw.utils.messages.serverMessages;


import it.polimi.ingsw.client.CliStatesController;
import it.polimi.ingsw.client.reducedmodel.ReducedGame;
import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.network.Client;

public class GameResumedMessage extends GameSetupMessage {

    private final User savedUserInstance;
    private final User currentUser;

    public GameResumedMessage(ReducedGame game, User savedUserInstance, User playerInTurn) {
        super(game);
        this.savedUserInstance = savedUserInstance;
        this.currentUser = playerInTurn;
    }

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
