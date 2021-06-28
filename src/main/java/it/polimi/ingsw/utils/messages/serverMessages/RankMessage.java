package it.polimi.ingsw.utils.messages.serverMessages;

import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.Pair;

import java.util.List;

public class RankMessage implements ServerMessage{

    private final List<Pair<User, Integer>> rank;

    public RankMessage(List<Pair<User, Integer>> rank) {
        this.rank = rank;
    }

    public List<Pair<User, Integer>> getRank() {
        return rank;
    }

    @Override
    public void handleMessage(Client handler) {
        handler.getUI().render(this);
    }
}
