package it.polimi.ingsw.Client.clientstates.cli;

import it.polimi.ingsw.Client.clientstates.AbstractInitialLeaderChoice;
import it.polimi.ingsw.Client.view.CLI;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ClientMessages.LeaderChoiceMessage;

import java.io.InvalidObjectException;
import java.util.InputMismatchException;
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
        int leaderCard1Index = 0;
        int leaderCard2Index = 0;
        boolean selectionDone = false;
        System.out.println("Choose index of the first leader card you want to discard: ");
        do {
            try {
                leaderCard1Index = Integer.parseInt(input.next());
                if (leaderCard1Index < 1 || leaderCard1Index > 4) {
                    throw new NumberFormatException();
                } else {
                    this.leaderCard1Index = leaderCard1Index;
                    selectionDone = true;
                }
            }
            catch(NumberFormatException e){
                System.out.print("Invalid index\nSelect index between [1 - 4]: ");
            }
        } while (!selectionDone);
        System.out.println("First chosen index: "+leaderCard1Index);
        selectionDone = false;
        System.out.println("Choose index of the second leader card you want to discard: ");
        do {
            try {
            leaderCard2Index = Integer.parseInt(input.next());
            if (leaderCard2Index < 1 || leaderCard2Index > 4) {
                throw new NumberFormatException();
            } else {
                if (leaderCard2Index == this.leaderCard1Index) {
                    throw new InputMismatchException();
                } else {
                    this.leaderCard2Index = leaderCard2Index;
                    selectionDone = true;
                }}
            }
            catch(NumberFormatException e) {
                System.out.print("Invalid index\nSelect index between [1 - 4]: ");
            }
            catch(InputMismatchException e) {
                System.out.print("Invalid index\nChoose index different from previous one: ");
            }
        } while (!selectionDone);
        System.out.println("Second chosen index: "+this.leaderCard2Index);
        this.leaderCard2Index = leaderCard2Index;
    }

}
