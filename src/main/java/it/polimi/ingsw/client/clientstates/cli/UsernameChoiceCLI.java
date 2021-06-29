package it.polimi.ingsw.client.clientstates.cli;

import it.polimi.ingsw.client.clientstates.AbstractUserNameChoice;
import it.polimi.ingsw.network.Client;

import java.util.Scanner;

/**
 * This class represents the CLI-specific username choice state which is reached by the client
 * at the launch of the game.
 */
public class UsernameChoiceCLI extends AbstractUserNameChoice {
    /**
     * The Scanner used to receive inputs from user.
     */
    private final Scanner input = new Scanner(System.in);

    /**
     * Initializes reference to client.
     */
    public UsernameChoiceCLI(Client client) {
        super(client);
    }

    /**
     * Main method of the class. It asks the user a nickname and sends it to the server.
     */
    @Override
    public void manageUserInteraction() {
        String nickname = input.nextLine();
        messageToSend.setNickname(nickname);
        client.sendMessage(messageToSend);
    }
}
