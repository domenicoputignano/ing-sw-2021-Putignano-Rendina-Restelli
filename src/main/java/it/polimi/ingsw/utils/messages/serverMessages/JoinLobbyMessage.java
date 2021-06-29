package it.polimi.ingsw.utils.messages.serverMessages;


import it.polimi.ingsw.network.Client;

import java.util.List;

/**
 * Class representing a message sent from server when a player is added to a lobby in order to notifies
 * other members inside it.
 */
public class JoinLobbyMessage implements ServerMessage {
    /**
     * Last player added to the lobby
     */
    private final String lastAwaitingGuest;
    /**
     * Number of player still required before starting the game.
     */
    private int numOfMissingPlayers;
    /**
     * List of all player inside the lobby.
     */
    private final List<String> awaitingGuests;

    public JoinLobbyMessage(String lastAwaitingGuest, List<String> awaitingGuests, int numOfMissingPlayers) {
        this.lastAwaitingGuest = lastAwaitingGuest;
        this.numOfMissingPlayers = numOfMissingPlayers;
        this.awaitingGuests = awaitingGuests;
    }

    /**
     * Method called by client in order to show who is inside the lobby at runtime.
     * @param handler {@link Client} instance that manages the update itself.
     */
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
