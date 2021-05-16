package it.polimi.ingsw.Client.clientstates.cli;

import it.polimi.ingsw.Client.clientstates.AbstractGameModeChoice;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ClientMessages.GameModeChoiceMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;

import java.util.Scanner;

public class GameModeChoiceCLI extends AbstractGameModeChoice {

    private Scanner input = new Scanner(System.in);

    public GameModeChoiceCLI(Client client) {
        super(client);
    }

    @Override
    public void render(ServerMessage message) {
        System.out.println("Choose game mode [Multiplayer| Solo]: ");
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
        messageToSend = new GameModeChoiceMessage(gameModeChoice);
        client.sendMessage(messageToSend);
    }


}
