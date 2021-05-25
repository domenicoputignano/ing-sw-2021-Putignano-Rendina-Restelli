package it.polimi.ingsw.Client.clientstates.cli;

import it.polimi.ingsw.Client.clientstates.AbstractInitialLeaderChoice;
import it.polimi.ingsw.Client.view.CLI;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ClientMessages.LeaderChoiceMessage;

import java.util.Scanner;

public class InitialLeaderChoiceCLI extends AbstractInitialLeaderChoice {

    private final CLI cli;
    private final Scanner input = new Scanner(System.in);

    public InitialLeaderChoiceCLI(Client client) {
        super(client);
        cli = (CLI) client.getUI();
    }

    @Override
    public void manageUserInteraction() {
        cli.showLeaderCards();
        setLeaderCardsIndexes();
        messageToSend = new LeaderChoiceMessage(leaderCard1Index, leaderCard2Index);
        client.sendMessage(messageToSend);
    }

    private void setLeaderCardsIndexes() {
        int leaderCard1Index;
        int leaderCard2Index;
        boolean selectionDone = false;
        System.out.println("Choose index of the first leader card you want to discard: ");
        do {
            leaderCard1Index = input.nextInt();
            if (leaderCard1Index < 1 || leaderCard1Index > 4) {
                System.out.println("Invalid index, try again ");
            } else {
                this.leaderCard1Index = leaderCard1Index;
                selectionDone = true;
            }
        } while (!selectionDone);
        selectionDone = false;
        System.out.println("Choose index of the second leader card you want to discard: ");
        do {
            leaderCard2Index = input.nextInt();
            if (leaderCard2Index < 1 || leaderCard2Index > 4) {
                System.out.println("Invalid index, try again ");
            } else {
                if (leaderCard2Index == this.leaderCard1Index) {
                    System.out.println("Index already selected, try again ");
                } else {
                    this.leaderCard2Index = leaderCard1Index;
                    selectionDone = true;
                }
            }
        } while (!selectionDone);
        this.leaderCard2Index = leaderCard2Index;
    }

}
