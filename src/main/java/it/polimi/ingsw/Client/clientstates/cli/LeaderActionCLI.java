package it.polimi.ingsw.Client.clientstates.cli;

import it.polimi.ingsw.Client.ReducedPlayer;
import it.polimi.ingsw.Client.clientstates.AbstractLeaderAction;
import it.polimi.ingsw.Client.view.CLI;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;

import java.util.Scanner;

public class LeaderActionCLI extends AbstractLeaderAction {

    private Scanner input = new Scanner(System.in);
    private final CLI cli;

    public LeaderActionCLI(CLI cli) {
        this.cli = cli;
    }

    @Override
    public void render(ServerMessage message){
    }

    @Override
    public void manageUserInteraction() {
        if(client.getGame().getCurrPlayer().isAvailableLeaderAction()){
            System.out.println("What leader action do you want to perform (ACTIVATE|DISCARD) ? ");
            String leaderActionChosen = chooseLeaderAction(input);
            messageToSend.setToDiscard(leaderActionChosen.equalsIgnoreCase("discard"));
            System.out.printf("Well, you have %d leader card available, which one do you want to %s (value in [1 - %d]) ? ",
                    client.getGame().getCurrPlayer().getNumOfAvailableLeaderCards(), leaderActionChosen, client.getGame().getCurrPlayer().getNumOfAvailableLeaderCards());
            int chosenIndex = chooseCardIndex(input);
            messageToSend.setIndex(chosenIndex);
            client.sendMessage(messageToSend);
        } else {
            System.out.println("Oh no! Seems that all leader action has been performed. Try another action ");
        }
    }

    //TODO stub created only for test purposes
    public void manageUserInteraction(ReducedPlayer player, Scanner scanner) {
        if(player.isAvailableLeaderAction()){
            System.out.println("What leader action do you want to perform (ACTIVATE|DISCARD) ? ");
            String leaderActionChosen = chooseLeaderAction(scanner);
            messageToSend.setToDiscard(leaderActionChosen.equalsIgnoreCase("discard"));
            System.out.printf("Well, you have %d leader card available, which one do you want to %s (value in [1 - %d]) ? ",
                    player.getNumOfAvailableLeaderCards(), leaderActionChosen, player.getNumOfAvailableLeaderCards());
            int chosenIndex = chooseCardIndex(scanner);
            messageToSend.setIndex(chosenIndex);
            //client.sendMessage(messageToSend);
        } else {
            System.out.println("Oh no! Seems that all leader action has been performed. Try another action ");
        }
    }


    /* Method that iterate until is chosen a valid action over a leaderCard */
    private String chooseLeaderAction(Scanner input) {
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



    private int chooseCardIndex(Scanner input) {
        int chosenIndex;
        do {
            chosenIndex = input.nextInt();
            if(indexNotInRange(chosenIndex)) {
                System.out.println("Invalid index, try again ");
            }
        } while(indexNotInRange(chosenIndex));
        return chosenIndex;
    }

    private boolean indexNotInRange(int chosenIndex) {
        return (chosenIndex < 1 || chosenIndex > client.getGame().getCurrPlayer().getNumOfAvailableLeaderCards());
    }



}
