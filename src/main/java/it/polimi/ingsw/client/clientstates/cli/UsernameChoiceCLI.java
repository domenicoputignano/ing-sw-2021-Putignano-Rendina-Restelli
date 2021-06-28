package it.polimi.ingsw.client.clientstates.cli;

import it.polimi.ingsw.client.clientstates.AbstractUserNameChoice;
import it.polimi.ingsw.network.Client;

import java.util.Scanner;

public class UsernameChoiceCLI extends AbstractUserNameChoice {
    private final Scanner input = new Scanner(System.in);

    public UsernameChoiceCLI(Client client) {
        super(client);
    }


    @Override
    public void manageUserInteraction() {
        String nickname = input.nextLine();
        messageToSend.setNickname(nickname);
        client.sendMessage(messageToSend);
    }
}
