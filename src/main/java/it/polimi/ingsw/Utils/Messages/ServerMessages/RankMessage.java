package it.polimi.ingsw.Utils.Messages.ServerMessages;

import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Pair;

import java.util.List;
import java.util.Map;

public class RankMessage implements ServerMessage{

    private final List<Pair<User, Integer>> rank;

    public RankMessage(List<Pair<User, Integer>> rank) {
        this.rank = rank;
    }

    @Override
    public void handleMessage(Client handler) {

    }
}
