package it.polimi.ingsw.Utils.Messages.ServerMessages;


import it.polimi.ingsw.Client.ClientStatesController;
import it.polimi.ingsw.Client.reducedmodel.ReducedGame;
import it.polimi.ingsw.Network.Client;


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
        ClientStatesController.updateClientState(this, handler.getUI());
    }
}
