package it.polimi.ingsw.client.clientstates.cli;

import it.polimi.ingsw.client.clientstates.AbstractTakeResourcesFromMarket;
import it.polimi.ingsw.client.reducedmodel.ReducedMarble;
import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.commons.ColorMarble;
import it.polimi.ingsw.exceptions.BackToMenuException;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.MarbleDestination;
import it.polimi.ingsw.utils.MarketChoice;
import it.polimi.ingsw.utils.Pair;

import java.util.Scanner;

/**
 * This class represents the CLI-specific state that is reached when the client chooses to
 * take resources from the {@link it.polimi.ingsw.model.marketTray.MarketTray} during his turn.
 */
public class TakeResourcesFromMarketCLI extends AbstractTakeResourcesFromMarket {
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
    public TakeResourcesFromMarketCLI(Client client) {
        super(client);
        cli = (CLI) client.getUI();
    }

    /**
     * Main method of the class. It leads the interaction with the user during different phases of this action.
     */
    @Override
    public void manageUserInteraction() {
        try {
            // show the Market Tray
            cli.showMarketTray();
            // if the player wants to go back, return to the action choice state
            cli.playerWantsToGoBack();
            // asks if he wants to take a row or a column of marbles
            chooseRowColumn();
            // asks the index of the row/column he wants to take
            chooseIndex();
            // compute the selected marble from the chosen row/column of the market tray
            computeSelectedMarbles();
            System.out.println("You obtained these marbles: " + selectedMarbles);
            // for each selected marble asks where to put it
            for (ReducedMarble marble : selectedMarbles) {
                setupMarble(marble);
            }
            // sends the message to the server
            client.sendMessage(message);
        } catch(BackToMenuException e) {
            cli.returnToMenu();
        }
    }

    /**
     * Method used to handle the user's choice between a row or a column of the Market Tray.
     */
    private void chooseRowColumn() {
        System.out.println("You chose to take resources from Market. Now choose ROW|COL");
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
                System.out.println("Invalid choice, please choose again ROW|COL");
            }
        } while (!choiceOK);
    }

    /**
     * Method used to handle the choice of the index of the row/column the user wants to take.
     */
    private void chooseIndex() {
        System.out.println("You chose " + message.getPlayerChoice());
        //int choice;
        int choice;
        boolean choiceOK = false;
        if(message.getPlayerChoice() == MarketChoice.ROW) {
            System.out.println("Choose the index of the row [1-3]");
            do {
                try {
                    String string = input.next();
                    choice = Integer.parseInt(string);
                    if(choice==1|| choice==2|| choice==3) {
                        message.setIndex(choice);
                        choiceOK = true;
                    } else {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid index, choose again [1-3]");
                }
            } while(!choiceOK);
        }
        else {
            System.out.println("Choose the index of the column [1-4]");
            do {
                try {
                    String string = input.next();
                    choice = Integer.parseInt(string);
                    if(choice==1|| choice==2|| choice==3 || choice == 4) {
                        message.setIndex(choice);
                        choiceOK = true;
                    } else {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid index, choose again [1-3]");
                }
            } while(!choiceOK);
        }
    }

    /**
     * Method used to ask where the user wants to place the resource related to the given marble.
     * If the marble is white and the user has two active convert marble effects he has to choice which one he wants
     * to activate.
     * @param marble the marble the user have to position.
     */
    private void setupMarble(ReducedMarble marble) {
        if(marble.getColorMarble() == ColorMarble.WHITE){
            if(getConvertMarbleActiveEffects().size() == 1) {
                System.out.println("White marble : converted to " + getConvertMarbleActiveEffects().get(0));
                message.addWhereToPutMarbles(new Pair<>(marble, cli.chooseMarbleDestination()));
            }
            else if(getConvertMarbleActiveEffects().size() == 2) {
                System.out.print("Found two convert marble active effects, choose which one you want to activate [1|2]");
                message.addWhiteEffect(chooseConvertMarbleEffect());
                message.addWhereToPutMarbles(new Pair<>(marble, cli.chooseMarbleDestination()));
            } else {
                System.out.println("White marble : you don't have any convert marble effect active so you won't get any resource from this marble");
                message.addWhereToPutMarbles(new Pair<>(marble,MarbleDestination.NOTNEEDED));
            }
        } else if (marble.getColorMarble() == ColorMarble.RED) {
            System.out.println("Red marble : you get a faith point");
            message.addWhereToPutMarbles(new Pair<>(marble, MarbleDestination.NOTNEEDED));
        } else if (marble.getColorMarble() == ColorMarble.BLUE) {
            System.out.println("Blue marble : you get a shield");
            message.addWhereToPutMarbles(new Pair<>(marble, cli.chooseMarbleDestination()));
        } else if (marble.getColorMarble() == ColorMarble.GREY) {
            System.out.println("Grey marble : you get a stone");
            message.addWhereToPutMarbles(new Pair<>(marble, cli.chooseMarbleDestination()));
        } else if (marble.getColorMarble() == ColorMarble.PURPLE) {
            System.out.println("Purple marble : you get a servant");
            message.addWhereToPutMarbles(new Pair<>(marble, cli.chooseMarbleDestination()));
        } else if (marble.getColorMarble() == ColorMarble.YELLOW) {
            System.out.println("Yellow marble : you get a coin");
            message.addWhereToPutMarbles(new Pair<>(marble, cli.chooseMarbleDestination()));
        }
    }

    /**
     * Method used to choose which convert marble effect the user wants to activate.
     * @return the index of the convert marble effect chosen.
     */
    private int chooseConvertMarbleEffect() {
        boolean choiceOK = false;
        int choice;
        do {
            choice = input.nextInt();
            if(choice == 1 || choice == 2) {
                choiceOK = true;
            } else {
                System.out.print("Invalid choice, please choose again [1|2]");
            }
        } while (!choiceOK);
        return choice;
    }

}
