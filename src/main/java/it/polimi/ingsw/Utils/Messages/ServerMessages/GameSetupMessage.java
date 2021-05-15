package it.polimi.ingsw.Utils.Messages.ServerMessages;


import it.polimi.ingsw.Client.ReducedGame;
import it.polimi.ingsw.Client.ReducedMarketTray;
import it.polimi.ingsw.Client.ReducedPlayer;
import it.polimi.ingsw.Commons.Deck;
import it.polimi.ingsw.Network.Client;
import java.util.List;

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
    }
}
