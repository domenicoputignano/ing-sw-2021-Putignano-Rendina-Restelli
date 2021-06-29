package it.polimi.ingsw.client.clientstates.cli;

import it.polimi.ingsw.client.clientstates.AbstractNumOfPlayersChoice;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.clientMessages.NumOfPlayerChoiceMessage;

import java.util.Scanner;

/**
 * This class represents the CLI-specific number of players choice state which is reached by the client
 * after the game mode choice in {@link it.polimi.ingsw.model.MultiPlayerMode}.
 */
public class NumOfPlayersChoiceCLI extends AbstractNumOfPlayersChoice {

    /**
     * The Scanner used to receive inputs from user.
     */
    private final Scanner input = new Scanner(System.in);

    /**
     * Initializes references to client.
     */
    public NumOfPlayersChoiceCLI(Client client) {
        super(client);
    }

    /**
     * Main method of the class. It leads the interaction with the user asking him the number of players he wants
     * and sending a message with the answer to the server.
     */
    @Override
    public void manageUserInteraction() {
        int numOfPlayers;
        do {
            try {
                numOfPlayers = Integer.parseInt(input.next());
                if (numOfPlayers < 2 || numOfPlayers > 4) {
                    throw new NumberFormatException();
                }
            } catch(NumberFormatException e) {
                System.out.print("Number of players selected not valid, please choose again\nNumber of players: ");
                numOfPlayers = 0;
            }
        } while (numOfPlayers < 2);
        messageToSend = new NumOfPlayerChoiceMessage(numOfPlayers);
        client.sendMessage(messageToSend);
        client.getUI().changeCliState(new LobbyCLI(client));
        client.getUI().getClientState().manageUserInteraction();

    }
}