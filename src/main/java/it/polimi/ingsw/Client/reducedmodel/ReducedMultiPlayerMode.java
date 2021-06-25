package it.polimi.ingsw.Client.reducedmodel;

import it.polimi.ingsw.Commons.Deck;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.NewTurnUpdate;

import java.util.List;

public class ReducedMultiPlayerMode extends ReducedGame {

    public ReducedMultiPlayerMode(List<ReducedPlayer> players, List<Deck> decks, ReducedMarketTray marketTray, ReducedPlayer currPlayer) {
        super(players, decks, marketTray, false);
        this.currPlayer = currPlayer;
    }

    public synchronized void nextTurn(NewTurnUpdate message) {
        currPlayer = this.getPlayer(message.getCurrentUser());
    }

    @Override
    public void printPlayers() {
        System.out.println("Here there are players that are currently playing");
        players.stream().filter(x -> x.getNickname().isActive()).forEach(
                x -> System.out.println("User : "+x.getNickname().getNickname())
        );
    }
}
