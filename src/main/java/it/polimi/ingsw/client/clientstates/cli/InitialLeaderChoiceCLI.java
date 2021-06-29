package it.polimi.ingsw.client.clientstates.cli;

import it.polimi.ingsw.client.clientstates.AbstractInitialLeaderChoice;
import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.clientMessages.LeaderChoiceMessage;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class represents the CLI-specific state that is reached when the client has to
 * choose which leader cards to discard during the initial choices phase of the game.
 */
public class InitialLeaderChoiceCLI extends AbstractInitialLeaderChoice {
    /**
     * The Scanner used to receive inputs from user.
     */
    private final Scanner input = new Scanner(System.in);
    /**
     * The CLI instance this state refers to.
     */
    private final CLI cli;

    /**
     * Initializes references to CLI and client.
     */
    public InitialLeaderChoiceCLI(Client client) {
        super(client);
        cli = (CLI) client.getUI();
    }

    /**
     * Main method of the class. It leads the interaction with the user during different phases of this action.
     */
    @Override
    public void manageUserInteraction() {
        System.out.println("\n\n\nTake a look to these cards, you need to discard two of them\nRemaining will follow you during the game");
        // show leader cards
        cli.showLeaderCards(client.getGame().getPlayer(client.getUser()).getPersonalBoard());
        // handle the selection of the leader cards the user wants to discard
        setLeaderCardsIndexes();
        // compile the message with the user's choice and send it to the server
        messageToSend = new LeaderChoiceMessage(leaderCard1Index, leaderCard2Index);
        client.sendMessage(messageToSend);
    }

    /**
     * Method used to handle the selection of two indexes representing the leader cards the player wants to discard.
     */
    private void setLeaderCardsIndexes() {
        int leaderCard1Index = 0;
        int leaderCard2Index = 0;
        boolean selectionDone = false;
        System.out.print("Choose index of the first leader card you want to discard: ");
        // asks the index of the first leader card to discard
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
        System.out.print("Choose index of the second leader card you want to discard: ");
        // asks the index of the second leader card to discard
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
