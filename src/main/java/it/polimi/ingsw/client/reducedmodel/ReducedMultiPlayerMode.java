package it.polimi.ingsw.client.reducedmodel;

import it.polimi.ingsw.commons.Deck;
import it.polimi.ingsw.utils.messages.serverMessages.Updates.NewTurnUpdate;

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
