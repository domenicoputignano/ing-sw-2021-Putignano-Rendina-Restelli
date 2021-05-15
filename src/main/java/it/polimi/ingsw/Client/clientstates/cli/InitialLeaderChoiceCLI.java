package it.polimi.ingsw.Client.clientstates.cli;

import it.polimi.ingsw.Client.clientstates.AbstractInitialLeaderChoice;
import it.polimi.ingsw.Client.view.CLI;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;

import java.util.Scanner;

public class InitialLeaderChoiceCLI extends AbstractInitialLeaderChoice {

    private final CLI cli;
    private Scanner input = new Scanner(System.in);

    public InitialLeaderChoiceCLI(Client client) {
        super(client);
        cli = (CLI) client.getUI();
    }

    @Override
    public void render(ServerMessage message) {

    }

    @Override
    public void manageUserInteraction() {
        ((CLI)client.getUI()).showLeaderCards();
    }


}
