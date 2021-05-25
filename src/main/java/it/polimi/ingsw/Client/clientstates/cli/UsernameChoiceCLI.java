package it.polimi.ingsw.Client.clientstates.cli;

import it.polimi.ingsw.Client.clientstates.AbstractUserNameChoice;
import it.polimi.ingsw.Network.Client;

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
        //TODO cambiare a seconda che il nickname venga accettato o rifiutato
        client.bindUser(nickname);
        client.sendMessage(messageToSend);
    }
}
