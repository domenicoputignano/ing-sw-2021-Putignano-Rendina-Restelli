package it.polimi.ingsw.Client.clientstates.cli;

import it.polimi.ingsw.Client.clientstates.AbstractClientState;
import it.polimi.ingsw.Client.clientstates.AbstractNumOfPlayersChoice;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ClientMessages.NumOfPlayerChoiceMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;

import java.util.Scanner;

public class NumOfPlayersChoiceCLI extends AbstractNumOfPlayersChoice {

    private Scanner input = new Scanner(System.in);

    public NumOfPlayersChoiceCLI(Client client) {
        super(client);
    }

    @Override
    public void render(ServerMessage message) {
        System.out.println("Choose the number of players [2-4]: ");
    }

    @Override
    public void manageUserInteraction() {
        int numOfPlayers;
        do {
            numOfPlayers = input.nextInt();
            if(numOfPlayers < 1 || numOfPlayers > 4) {
                System.out.println("Number of players selected not valid, please choose again ");
            }
        } while(numOfPlayers < 1 || numOfPlayers > 4);
        messageToSend = new NumOfPlayerChoiceMessage(numOfPlayers);
        client.sendMessage(messageToSend);
    }
}
