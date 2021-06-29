package it.polimi.ingsw.client.clientstates.cli;

import it.polimi.ingsw.client.clientstates.AbstractGameModeChoice;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.clientMessages.GameModeChoiceMessage;

import java.util.Scanner;

/**
 * This class represents the CLI-specific game mode choice state which is reached by the client
 * after the username choice.
 */
public class GameModeChoiceCLI extends AbstractGameModeChoice {

    /**
     * The Scanner used to receive inputs from user.
     */
    private final Scanner input = new Scanner(System.in);

    /**
     * Initializes reference to client.
     */
    public GameModeChoiceCLI(Client client) {
        super(client);
    }

    /**
     * Main method of the class. It leads the interaction with the user asking him the mode he wants to play and
     * parsing the answer until it is valid. Then sends a message containing the mode chosen to the server.
     */
    @Override
    public void manageUserInteraction() {
        String gameModeChoice;
        boolean choiceDone = false;
        do {
            gameModeChoice = input.next();
            if(gameModeChoice.equalsIgnoreCase("multiplayer")|| gameModeChoice.equalsIgnoreCase("multi")
            || gameModeChoice.equalsIgnoreCase("solo")) {
                choiceDone = true;
            } else {
                System.out.println("Error select a valid mode ");
            }
        } while(!choiceDone);
        if(gameModeChoice.equalsIgnoreCase("solo")) {
            client.getUI().setSoloMode(true);
            messageToSend = new GameModeChoiceMessage("solo");
        } else {
            messageToSend = new GameModeChoiceMessage("multiplayer");
        }
        client.sendMessage(messageToSend);

    }


}
