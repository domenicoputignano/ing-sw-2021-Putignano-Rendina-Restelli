package it.polimi.ingsw.Client.reducedmodel;

import it.polimi.ingsw.Commons.Deck;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.NewTurnUpdate;

import java.util.List;

public class ReducedMultiPlayerMode extends ReducedGame {

    public ReducedMultiPlayerMode(List<ReducedPlayer> players, List<Deck> decks, ReducedMarketTray marketTray) {
        super(players, decks, marketTray, false);
    }

    public void nextTurn(NewTurnUpdate message) {
        currPlayer = this.getPlayer(message.getCurrentUser());
    }

    @Override
    public void getPlayers() {

    }
}
