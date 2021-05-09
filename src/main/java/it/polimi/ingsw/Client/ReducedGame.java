package it.polimi.ingsw.Client;

import it.polimi.ingsw.Commons.Deck;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.ActivateProductionUpdate;

import java.util.List;

public abstract class ReducedGame {

    private List<ReducedPlayer> players;
    private ReducedTurn turn;
    private ReducedPlayer currPlayer;
    private ReducedMarketTray marketTray;
    private List<Deck> decks;



    public void performUpdate(ActivateProductionUpdate message) {

    }



}
