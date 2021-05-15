package it.polimi.ingsw.Client;

import it.polimi.ingsw.Commons.Deck;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.NewTurnUpdate;

import java.util.List;

public class ReducedMultiPlayerMode extends ReducedGame {

    public ReducedMultiPlayerMode(List<ReducedPlayer> players, List<Deck> decks, ReducedMarketTray marketTray) {
        super(players, decks, marketTray, false);
    }

    @Override
    public void nextTurn(NewTurnUpdate message) {
        currPlayer = getPlayer(message.getCurrentUser());
    }
}
