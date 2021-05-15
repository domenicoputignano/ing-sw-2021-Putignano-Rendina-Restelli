package it.polimi.ingsw.Utils.Messages.ServerMessages;


import it.polimi.ingsw.Client.ReducedMarketTray;
import it.polimi.ingsw.Client.ReducedPlayer;
import it.polimi.ingsw.Commons.Deck;
import it.polimi.ingsw.Network.Client;
import java.util.List;

public class GameSetupMessage implements ServerMessage {
    private List<ReducedPlayer> players;
    private List<Deck> decks;
    private ReducedMarketTray marketTray;


    public GameSetupMessage(List<ReducedPlayer> players, List<Deck> decks, ReducedMarketTray marketTray) {
        this.players = players;
        this.decks = decks;
        this.marketTray = marketTray;
    }


    @Override
    public void handleMessage(Client handler) {
        handler.setupGame(this);
    }
}
