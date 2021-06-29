package it.polimi.ingsw.utils.messages.serverMessages;

import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.Pair;

import java.util.List;

/**
 * Class representing final message received from server when a multiplayer mode game definitively ends.
 */
public class RankMessage implements ServerMessage{

    /**
     * Rank of the game. First pair represents user of player who won the game and its score.
     */
    private final List<Pair<User, Integer>> rank;

    public RankMessage(List<Pair<User, Integer>> rank) {
        this.rank = rank;
    }

    public List<Pair<User, Integer>> getRank() {
        return rank;
    }

    /**
     * Method called by client in order to show message itself.
     * @param handler {@link Client} instance that manages the update itself.
     */
    @Override
    public void handleMessage(Client handler) {
        handler.getUI().render(this);
    }
}
