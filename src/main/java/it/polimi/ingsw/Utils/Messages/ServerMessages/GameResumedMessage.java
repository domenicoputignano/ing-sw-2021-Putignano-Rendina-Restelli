package it.polimi.ingsw.Utils.Messages.ServerMessages;


import it.polimi.ingsw.Client.ClientStatesController;
import it.polimi.ingsw.Client.reducedmodel.ReducedGame;
import it.polimi.ingsw.Network.Client;

public class GameResumedMessage extends GameSetupMessage {
    public GameResumedMessage(ReducedGame game) {
        super(game);
    }

    public void handleMessage(Client handler) {
        handler.setupGame(this);
        handler.getUI().render(this);
        ClientStatesController.updateClientState(this, handler.getUI());
    }

}
