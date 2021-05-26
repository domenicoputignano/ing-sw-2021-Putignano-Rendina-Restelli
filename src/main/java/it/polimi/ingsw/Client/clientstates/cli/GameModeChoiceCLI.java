package it.polimi.ingsw.Client.clientstates.cli;

import it.polimi.ingsw.Client.clientstates.AbstractGameModeChoice;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ClientMessages.GameModeChoiceMessage;

import java.util.Scanner;

public class GameModeChoiceCLI extends AbstractGameModeChoice {

    private final Scanner input = new Scanner(System.in);

    public GameModeChoiceCLI(Client client) {
        super(client);
    }

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
        if(gameModeChoice.equalsIgnoreCase("solo")) client.getUI().setSoloMode(true);
        messageToSend = new GameModeChoiceMessage(gameModeChoice);
        client.sendMessage(messageToSend);

    }


}
