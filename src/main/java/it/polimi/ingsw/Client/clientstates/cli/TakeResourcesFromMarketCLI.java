package it.polimi.ingsw.Client.clientstates.cli;

import it.polimi.ingsw.Client.ReducedMarble;
import it.polimi.ingsw.Client.clientstates.AbstractTakeResourcesFromMarket;
import it.polimi.ingsw.Client.view.CLI;
import it.polimi.ingsw.Commons.ColorMarble;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.MarbleDestination;
import it.polimi.ingsw.Utils.MarketChoice;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;
import it.polimi.ingsw.Utils.Pair;

import java.util.Scanner;

public class TakeResourcesFromMarketCLI extends AbstractTakeResourcesFromMarket {
    private Scanner input = new Scanner(System.in);
    private final CLI cli;

    public TakeResourcesFromMarketCLI(Client client) {
        super(client);
        cli = (CLI) client.getUI();
    }

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
            setupMarble(marble);
        }
        client.sendMessage(message);
    }

    private void chooseRowColumn() {
        System.out.println("You chose to take resources from Market. Now choose ROW/COL");
        boolean choiceOK = false;
        String choice;
        do {
            choice = input.next().toUpperCase();
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

    private void setupMarble(ReducedMarble marble) {
        if(marble.getColorMarble() == ColorMarble.WHITE){
            if(getConvertMarbleActiveEffects().size() == 1) {
                System.out.println("White marble : converted to " + getConvertMarbleActiveEffects().get(0));
                message.addWhereToPutMarbles(new Pair<>(marble, chooseMarbleDestination()));
            }
            else if(getConvertMarbleActiveEffects().size() == 2) {
                System.out.println("Found two convert marble active effects, choose which one you want to activate [1|2]");
                message.addWhiteEffect(chooseConvertMarbleEffect());
                message.addWhereToPutMarbles(new Pair<>(marble, chooseMarbleDestination()));
            } else {
                System.out.println("White marble : you don't have any convert marble effect active so you won't get any resource from this marble");
            }
        } else if (marble.getColorMarble() == ColorMarble.RED) {
            System.out.println("Red marble : you get a faith point");
            message.addWhereToPutMarbles(new Pair<>(marble, MarbleDestination.NOTNEEDED));
        } else if (marble.getColorMarble() == ColorMarble.BLUE) {
            System.out.println("Blue marble : you get a shield");
            message.addWhereToPutMarbles(new Pair<>(marble, chooseMarbleDestination()));
        } else if (marble.getColorMarble() == ColorMarble.GREY) {
            System.out.println("Grey marble : you get a stone");
            message.addWhereToPutMarbles(new Pair<>(marble, chooseMarbleDestination()));
        } else if (marble.getColorMarble() == ColorMarble.PURPLE) {
            System.out.println("Purple marble : you get a servant");
            message.addWhereToPutMarbles(new Pair<>(marble, chooseMarbleDestination()));
        } else if (marble.getColorMarble() == ColorMarble.YELLOW) {
            System.out.println("Yellow marble : you get a coin");
            message.addWhereToPutMarbles(new Pair<>(marble, chooseMarbleDestination()));
        }
    }

    private int chooseConvertMarbleEffect() {
        boolean choiceOK = false;
        int choice;
        do {
            choice = input.nextInt();
            if(choice == 1 || choice == 2) {
                choiceOK = true;
            } else {
                System.out.println("Invalid choice, please choose again [1|2]");
            }
        } while (!choiceOK);
        return choice;
    }

    private MarbleDestination chooseMarbleDestination() {
        System.out.println("Where do you want to position it? [DEPOT1|DEPOT2|DEPOT3|EXTRA|DISCARD]");
        String choice;
        MarbleDestination destination;
        boolean destinationOK = false;
        do {
            choice = input.next().toUpperCase();
            destination = parseMarbleDestination(choice);
            if(destination!=null) {
                destinationOK = true;
            } else {
                System.out.println("Invalid choice, try again. [DEPOT1|DEPOT2|DEPOT3|EXTRA|DISCARD]");
            }
        } while(!destinationOK);
        return destination;
    }

    private MarbleDestination parseMarbleDestination(String choice) {
        switch (choice) {
            case "DEPOT1" : return MarbleDestination.DEPOT1;
            case "DEPOT2" : return MarbleDestination.DEPOT2;
            case "DEPOT3" : return MarbleDestination.DEPOT3;
            case "EXTRA" : return MarbleDestination.EXTRA;
            case "DISCARD" : return MarbleDestination.DISCARD;
            default : return null;
        }
    }

}
