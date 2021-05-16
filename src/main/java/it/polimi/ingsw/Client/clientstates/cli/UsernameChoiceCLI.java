package it.polimi.ingsw.Client.clientstates.cli;

import it.polimi.ingsw.Client.clientstates.AbstractUserNameChoice;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;

import java.util.Scanner;

public class UsernameChoiceCLI extends AbstractUserNameChoice {
    private Scanner input = new Scanner(System.in);

    public UsernameChoiceCLI(Client client) {
        super(client);
    }

    @Override
    public void render(ServerMessage message) {
        System.out.println("Choose your nickname: ");
    }

    @Override
    public void manageUserInteraction() {
        //System.out.println("Waiting for player interaction ");
        String nickname = input.nextLine();
        messageToSend.setNickname(nickname);
        client.sendMessage(messageToSend);
        System.out.println("Message sent");
    }
}
