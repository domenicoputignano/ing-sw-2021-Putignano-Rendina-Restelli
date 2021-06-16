package it.polimi.ingsw.Utils.Messages.ServerMessages;


import it.polimi.ingsw.Client.ClientStatesController;
import it.polimi.ingsw.Client.reducedmodel.ReducedGame;
import it.polimi.ingsw.Client.reducedmodel.ReducedPlayer;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Network.Client;

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
        ClientStatesController.updateClientState(this, handler.getUI());
    }

    public User getSavedUserInstance() {
        return savedUserInstance;
    }

    public User getCurrentUser() { return currentUser; }
}
