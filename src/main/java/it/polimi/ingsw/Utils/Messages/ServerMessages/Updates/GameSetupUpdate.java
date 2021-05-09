package it.polimi.ingsw.Utils.Messages.ServerMessages.Updates;

import it.polimi.ingsw.Client.ReducedMarketTray;
import it.polimi.ingsw.Client.ReducedPersonalBoard;
import it.polimi.ingsw.Commons.Deck;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Network.Client;

import java.util.List;

public class GameSetupUpdate extends UpdateMessage{
    private final int position;
    private final List<Deck> decks;
    private final ReducedMarketTray reducedMarketTray;

    public GameSetupUpdate(User user, ReducedPersonalBoard reducedPersonalBoard, int position, List<Deck> decks, ReducedMarketTray reducedMarketTray)
    {
        this.decks = decks;
        this.reducedMarketTray = reducedMarketTray;
        this.user = user;
        this.userPersonalBoard = reducedPersonalBoard;
        this.position = position;
    }

    @Override
    public void handleMessage(Client client) {

    }
}
