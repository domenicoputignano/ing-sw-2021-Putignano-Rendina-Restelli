package it.polimi.ingsw.Client.clientstates.cli;

import it.polimi.ingsw.Client.ReducedMarble;
import it.polimi.ingsw.Client.clientstates.AbstractTakeResourcesFromMarket;
import it.polimi.ingsw.Commons.ColorMarble;
import it.polimi.ingsw.Utils.MarketChoice;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;

import java.util.Scanner;

public class TakeResourcesFromMarketCLI extends AbstractTakeResourcesFromMarket {
    Scanner input = new Scanner(System.in);

    @Override
    public void render(ServerMessage message) {

    }

    @Override
    public void manageUserInteraction() {
        chooseRowColumn();
        chooseIndex();
        computeSelectedMarbles();
        System.out.println("You obtained these marbles: " + selectedMarbles);
        for(ReducedMarble marble : selectedMarbles) {
            chooseMarbleDestination(marble);
        }
    }

    private void chooseRowColumn() {
        System.out.println("You chose to take resources from Market. Now choose ROW/COL");
        boolean choiceOK = false;
        String choice;
        do {
            choice = input.nextLine().toUpperCase();
            if(choice.equals("ROW")) {
                message.setPlayerChoice(MarketChoice.ROW);
                choiceOK = true;
            } else if (choice.equals("COL")){
                message.setPlayerChoice(MarketChoice.COLUMN);
                choiceOK = true;
            } else {
                System.out.println("Invalid choice, please choose again ROW/COL");
            }
        } while (!choiceOK);
    }

    private void chooseIndex() {
        System.out.println("You chose " + message.getPlayerChoice());
        int choice;
        boolean choiceOK = false;
        if(message.getPlayerChoice() == MarketChoice.ROW) {
            System.out.println("Choose the index of the row [1-3]");
            do {
                choice = input.nextInt();
                if(choice==1||choice==2||choice==3) {
                    message.setIndex(choice);
                    choiceOK = true;
                } else {
                    System.out.println("Invalid index, choose again [1-3]");
                }
            } while(!choiceOK);
        } else {
            System.out.println("Choose the index of the column [1-4]");
            do {
                choice = input.nextInt();
                if(choice==1||choice==2||choice==3||choice==4) {
                    message.setIndex(choice);
                    choiceOK = true;
                } else {
                    System.out.println("Invalid index, choose again [1-4]");
                }
            } while(!choiceOK);
        }
    }

    private void chooseMarbleDestination(ReducedMarble marble) {
        if(marble.getColorMarble() == ColorMarble.WHITE){
            if(getConvertMarbleActiveEffects().size() == 1) {
                System.out.println("White marble : converted to " + getConvertMarbleActiveEffects().get(0));
                System.out.println("Where do you want to position it? [DEPOT|EXTRADEPOT]");
            }
        }
    }
}
