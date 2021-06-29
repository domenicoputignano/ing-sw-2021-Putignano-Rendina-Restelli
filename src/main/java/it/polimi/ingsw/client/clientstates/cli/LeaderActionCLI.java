package it.polimi.ingsw.client.clientstates.cli;

import it.polimi.ingsw.client.clientstates.AbstractLeaderAction;
import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.network.Client;

import java.util.Scanner;

/**
 * This class represents the CLI-specific state that is reached when the client wants to activate or
 * to discard a leader card during his turn.
 */
public class LeaderActionCLI extends AbstractLeaderAction {

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
    public LeaderActionCLI(Client client) {
        super(client);
        cli = (CLI) client.getUI();
    }

    /**
     * Main method of the class. It leads the interaction with the user during different phases of this action.
     */
    @Override
    public void manageUserInteraction() {
        if(client.getGame().getCurrPlayer().isAvailableLeaderAction()){
            // show leader cards
            cli.showLeaderCards(client.getGame().getPlayer(client.getUser()).getPersonalBoard());
            System.out.println("What leader action do you want to perform (ACTIVATE|DISCARD) ? ");
            // asks which type of leader action he wants to perform
            String leaderActionChosen = chooseLeaderAction();
            messageToSend.setToDiscard(leaderActionChosen.equalsIgnoreCase("discard"));
            System.out.printf("Well, you have %d leader card available, which one do you want to %s (value in [1 - %d]) ? ",
                    client.getGame().getCurrPlayer().getNumOfNotActiveLeaderCards(), leaderActionChosen, client.getGame().getCurrPlayer().getNumOfNotActiveLeaderCards());
            int chosenIndex = chooseCardIndex();
            // set compiled message and send it to the server
            messageToSend.setIndex(chosenIndex);
            client.sendMessage(messageToSend);
        } else {
            System.out.println("Oh no! Seems that all leader action has been performed. Try another action ");
            cli.returnToMenu();
        }
    }

    /**
     * Method used to handle the choice of the type of leader action the user wants to perform.
     * @return the type of leader action the user wants to perform.
     */
    private String chooseLeaderAction() {
        boolean doneSelection;
        String leaderActionChosen;
        do {
            leaderActionChosen = input.next();
            if(leaderActionChosen.equalsIgnoreCase("activate") ||
                    leaderActionChosen.equalsIgnoreCase("discard")) {
                doneSelection = true;
            }
            else {
                System.out.println("Invalid chosen action, try again ");
                doneSelection = false;
            }
        } while(!doneSelection);
        return leaderActionChosen;
    }

    /**
     * Method used to handle the choice of the index of the leader card to activate/discard.
     * @return the index chosen
     */
    private int chooseCardIndex() {
        int chosenIndex = 0;
        do {
            try {
                chosenIndex = Integer.parseInt(input.next());
                if (indexNotInRange(chosenIndex)) {
                    throw new NumberFormatException();
                }
            }
            catch(NumberFormatException e){
                System.out.printf("Invalid chosen index, please select again index between [1 - %d] : ", client.getGame().getCurrPlayer().getNumOfNotActiveLeaderCards());
            }
        } while(indexNotInRange(chosenIndex));
        if(client.getGame().getPlayer(client.getUser()).getNumOfNotActiveLeaderCards() == 1) {
            if(client.getGame().getPlayer(client.getUser()).getAvailableLeaderCards().size() > 1 &&
                    client.getGame().getPlayer(client.getUser()).getAvailableLeaderCards().get(0).isActive() &&
                    !client.getGame().getPlayer(client.getUser()).getAvailableLeaderCards().get(1).isActive())
                chosenIndex = 2;
            else chosenIndex = 1;
        }
        return chosenIndex;
    }

    /**
     * Utility method to check if the index chosen by the user is in the allowed range.
     * @param chosenIndex the index chosen by the user.
     * @return the check validity.
     */
    private boolean indexNotInRange(int chosenIndex) {
        return (chosenIndex < 1) || chosenIndex > client.getGame().getCurrPlayer().getNumOfNotActiveLeaderCards();
    }



}
