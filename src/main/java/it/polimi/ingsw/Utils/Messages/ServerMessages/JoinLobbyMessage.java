package it.polimi.ingsw.Utils.Messages.ServerMessages;


import it.polimi.ingsw.Network.Client;

import java.util.List;

public class JoinLobbyMessage implements ServerMessage {

    private final String lastAwaitingGuest;
    private int numOfMissingPlayers;
    private final List<String> awaitingGuests;

    public JoinLobbyMessage(String lastAwaitingGuest, List<String> awaitingGuests, int numOfMissingPlayers) {
        this.lastAwaitingGuest = lastAwaitingGuest;
        this.numOfMissingPlayers = numOfMissingPlayers;
        this.awaitingGuests = awaitingGuests;
    }

    @Override
    public void handleMessage(Client handler) {
        handler.getUI().render(this);
    }

    public String getLastAwaitingGuest() {
        return lastAwaitingGuest;
    }

    public List<String> getAwaitingGuests() {
        return awaitingGuests;
    }

    public int getNumOfMissingPlayers() {
        return numOfMissingPlayers;
    }
}
