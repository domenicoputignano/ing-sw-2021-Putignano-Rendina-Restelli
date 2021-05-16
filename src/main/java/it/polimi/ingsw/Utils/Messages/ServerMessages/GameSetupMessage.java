package it.polimi.ingsw.Utils.Messages.ServerMessages;


import it.polimi.ingsw.Client.ReducedGame;
import it.polimi.ingsw.Client.clientstates.cli.InitialLeaderChoiceCLI;
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
        //TODO
        if(handler.getUI().isCLI()) {
            handler.getUI().changeClientState(new InitialLeaderChoiceCLI(handler));
            handler.getUI().showUpdate(this);
            handler.getUI().manageUserInteraction();
        } else {
            //TODO metodo con GUI
        }
    }
}
