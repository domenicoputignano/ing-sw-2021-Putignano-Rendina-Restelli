package it.polimi.ingsw.utils.messages.serverMessages;


import it.polimi.ingsw.client.CliStatesController;
import it.polimi.ingsw.client.reducedmodel.ReducedGame;
import it.polimi.ingsw.network.Client;


public class GameSetupMessage implements ServerMessage {
    private final ReducedGame game;



    public GameSetupMessage(ReducedGame game) {
        this.game = game;
    }

    public ReducedGame getGame() {
        return game;
    }

    @Override
    public void handleMessage(Client handler) {
        handler.setupGame(this);
        handler.getUI().render(this);
        CliStatesController.updateCliState(this, handler.getUI());
    }
}
