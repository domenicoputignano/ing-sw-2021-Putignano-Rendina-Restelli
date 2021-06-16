package it.polimi.ingsw.Client.clientstates.cli;

import it.polimi.ingsw.Client.clientstates.AbstractLeaderAction;
import it.polimi.ingsw.Client.view.CLI;
import it.polimi.ingsw.Network.Client;

import java.util.Scanner;

public class LeaderActionCLI extends AbstractLeaderAction {

    private final Scanner input = new Scanner(System.in);
    private final CLI cli;

    public LeaderActionCLI(Client client) {
        super(client);
        cli = (CLI) client.getUI();
    }

    @Override
    public void manageUserInteraction() {
        if(client.getGame().getCurrPlayer().isAvailableLeaderAction()){
                cli.showLeaderCards(client.getGame().getPlayer(client.getUser()).getPersonalBoard());
                System.out.println("What leader action do you want to perform (ACTIVATE|DISCARD) ? ");
                String leaderActionChosen = chooseLeaderAction();
                messageToSend.setToDiscard(leaderActionChosen.equalsIgnoreCase("discard"));
                System.out.printf("Well, you have %d leader card available, which one do you want to %s (value in [1 - %d]) ? ",
                        client.getGame().getCurrPlayer().getNumOfNotActiveLeaderCards(), leaderActionChosen, client.getGame().getCurrPlayer().getNumOfNotActiveLeaderCards());
                int chosenIndex = chooseCardIndex();
                messageToSend.setIndex(chosenIndex);
                client.sendMessage(messageToSend);
        } else {
            System.out.println("Oh no! Seems that all leader action has been performed. Try another action ");
            cli.returnToMenu();
        }
    }



    /* Method that iterate until is chosen a valid action over a leaderCard */
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
        return chosenIndex;
    }

    private boolean indexNotInRange(int chosenIndex) {
        return (chosenIndex < 1) || chosenIndex > client.getGame().getCurrPlayer().getNumOfNotActiveLeaderCards();
    }



}
