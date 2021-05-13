package it.polimi.ingsw.Client.clientstates.cli;

import it.polimi.ingsw.Client.clientstates.AbstractLeaderAction;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;

import java.util.Scanner;

public class LeaderActionCLI extends AbstractLeaderAction {

    Scanner input = new Scanner(System.in);
    boolean toDiscard;

    @Override
    public void render(ServerMessage message) {

    }

    @Override
    public void manageUserInteraction() {
        if(client.getGame().getCurrPlayer().isAvailableLeaderAction()){
            System.out.println("What leader action do you want to perform (ACTIVATE|DISCARD) ? ");
            choseLeaderAction();

        } else {
            System.out.println("Oh no! Seems that all leader action has been performed. Try another action ");
        }
    }

    private String choseLeaderAction() {
        boolean doneSelection;
        String leaderActionChosen;
        do {
            leaderActionChosen = input.next();
            if(leaderActionChosen.equalsIgnoreCase("activate") ||
                    leaderActionChosen.equalsIgnoreCase("discard")) doneSelection = true;
            else {
                System.out.println("Invalid chosen action, try again ");
                doneSelection = false;
            }
        } while(!doneSelection);
        return leaderActionChosen;
    }
}
