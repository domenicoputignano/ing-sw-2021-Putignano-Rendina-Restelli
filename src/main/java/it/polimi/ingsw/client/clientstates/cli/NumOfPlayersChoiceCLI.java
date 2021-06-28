package it.polimi.ingsw.client.clientstates.cli;

import it.polimi.ingsw.client.clientstates.AbstractNumOfPlayersChoice;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.clientMessages.NumOfPlayerChoiceMessage;

import java.util.Scanner;

public class NumOfPlayersChoiceCLI extends AbstractNumOfPlayersChoice {

    private final Scanner input = new Scanner(System.in);

    public NumOfPlayersChoiceCLI(Client client) {
        super(client);
    }

    @Override
    public void manageUserInteraction() {
        int numOfPlayers = 0;
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