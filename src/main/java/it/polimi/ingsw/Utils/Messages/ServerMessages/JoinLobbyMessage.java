package it.polimi.ingsw.Utils.Messages.ServerMessages;


import it.polimi.ingsw.Network.Client;

public class JoinLobbyMessage implements ServerMessage {

    private final String awaitingGuest;
    private int numOfMissingPlayers;

    public JoinLobbyMessage(String awaitingGuest, int numOfMissingPlayers) {
        this.awaitingGuest = awaitingGuest;
        this.numOfMissingPlayers = numOfMissingPlayers;
    }

    @Override
    public void handleMessage(Client handler) {
        handler.getUI().render(this);
    }

    public String getAwaitingGuest() {
        return awaitingGuest;
    }

    public int getNumOfMissingPlayers() {
        return numOfMissingPlayers;
    }
}
