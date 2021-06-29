package it.polimi.ingsw.client.reducedmodel;

import it.polimi.ingsw.commons.Deck;
import it.polimi.ingsw.utils.messages.serverMessages.Updates.NewTurnUpdate;

import java.util.List;
/**
 * This class represents a simplified version of a MultiPlayerMode with respect to the server's class.
 * It contains only the information required client side.
 */
public class ReducedMultiPlayerMode extends ReducedGame {
    /**
     * Initialize an instance by setting players,decks,marketTray,currPlayer of the MultiPlayer Game.
     */
    public ReducedMultiPlayerMode(List<ReducedPlayer> players, List<Deck> decks, ReducedMarketTray marketTray, ReducedPlayer currPlayer) {
        super(players, decks, marketTray, false);
        this.currPlayer = currPlayer;
    }

    /**
     * method used to end the current player's turn and pass the turn to the next player
     * @param message New Turn update message
     * @see NewTurnUpdate
     */
    public synchronized void nextTurn(NewTurnUpdate message) {
        currPlayer = this.getPlayer(message.getCurrentUser());
    }

    /**
     * Return representation of the players in the game used by command line interface.
     */
    @Override
    public void printPlayers() {
        System.out.println("Here there are players that are currently playing");
        players.stream().filter(x -> x.getNickname().isActive()).forEach(
                x -> System.out.println("User : "+x.getNickname().getNickname())
        );
    }
}
