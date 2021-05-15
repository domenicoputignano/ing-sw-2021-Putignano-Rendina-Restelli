package it.polimi.ingsw.Client;

import it.polimi.ingsw.Commons.Deck;

import java.util.List;

public class ReducedMultiPlayerMode extends ReducedGame {

    public ReducedMultiPlayerMode(List<ReducedPlayer> players, List<Deck> decks, ReducedMarketTray marketTray) {
        super(players, decks, marketTray, false);
    }

    @Override
    public void nextTurn() {

    }
}
