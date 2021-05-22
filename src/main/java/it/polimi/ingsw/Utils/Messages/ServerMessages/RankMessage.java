package it.polimi.ingsw.Utils.Messages.ServerMessages;

import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Network.Client;

import java.util.Map;

public class RankMessage implements ServerMessage{

    private Map<User,Integer> rank;

    public RankMessage(Map<User, Integer> rank) {
        this.rank = rank;
    }

    @Override
    public void handleMessage(Client handler) {

    }
}
