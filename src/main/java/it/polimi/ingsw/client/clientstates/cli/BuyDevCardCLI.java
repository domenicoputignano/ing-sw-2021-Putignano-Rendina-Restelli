package it.polimi.ingsw.client.clientstates.cli;

import it.polimi.ingsw.client.clientstates.AbstractBuyDevCard;
import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.commons.CardType;
import it.polimi.ingsw.commons.ColorCard;
import it.polimi.ingsw.exceptions.BackToMenuException;
import it.polimi.ingsw.exceptions.InterruptedActionException;
import it.polimi.ingsw.network.Client;

import java.util.Scanner;

/**
 * This class represents the CLI-specific state that is reached when the player in turn
 * wants to buy a {@link it.polimi.ingsw.commons.DevelopmentCard} during his turn.
 */
public class BuyDevCardCLI extends AbstractBuyDevCard {
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
    public BuyDevCardCLI(Client client) {
        super(client);
        cli = (CLI)client.getUI();
    }

    /**
     * Main method of the class. It leads the interaction with the user during different phases of this action.
     */
    @Override
    public void manageUserInteraction() {
        try {
            System.out.println("Decks are shown below");
            // show decks
            cli.printDecks();
            // if the player wants to go back, return to the action choice state
            cli.playerWantsToGoBack();
            try {
                // asks to the user the level and the color of the card he wants to buy
                chooseCardType();
                // computes the actual cost of the selected card applying the available sales
                computeActualCost(takeDeckFromCardType(messageToSend.getType()));
                if (!checkCostRequiredCardType(actualCost)) {
                    System.out.println("You don't have enough resources to buy this card! Choose another one");
                    throw new InterruptedActionException();
                }
                // asks where the user wants to position the card
                chooseSlotDestination();
                // asks instructions on how to take resources needed to buy the selected card
                messageToSend.setHowToTakeResources(cli.askInstructionsOnHowToTakeResources(actualCost));
                // sends the compiled message to the server
                client.sendMessage(messageToSend);
            } catch (InterruptedActionException e) {
                cli.returnToActionBeginning(new BuyDevCardCLI(this.client));
            }
        } catch (BackToMenuException e) {
            cli.returnToMenu();
        }
    }

    /**
     * Method which handles the choice of the slot where the user wants to put the card.
     * @throws InterruptedActionException thrown when the user can't position the selected card.
     */
    private void chooseSlotDestination() throws InterruptedActionException {
        System.out.println("Which slot do you want to put the card on? [1-3]");
        int choice;
        int attempts = 1;
        boolean slotDestinationChoiceOK = false;
        do {
            try {
                choice = Integer.parseInt(input.next());
                if (choice == 1 || choice == 2 || choice == 3) {
                    if (canActivateCardOnThisSlot(choice - 1)) {
                        slotDestinationChoiceOK = true;
                        messageToSend.setDestinationSlot(choice);
                    } else {
                        System.out.println("You can't put the card on this slot, choose another slot. [1-3]");
                        attempts = attempts + 1;
                        if (attempts == 4) {
                            throw new InterruptedActionException();
                        }
                    }
                }
            } catch(NumberFormatException e) {
                System.out.println("Invalid choice, which slot do you want to put the card on? [1-3]");
            }
        } while (!slotDestinationChoiceOK);
    }

    /**
     * Method which handles the choice of level and color of the card the player wants to buy.
     */
    private void chooseCardType() {
        System.out.println("Which type of card do you want to buy? (level|color) ");
        String choice;
        boolean cardTypeChoiceOK = false;
        do {
            choice = input.next();
            CardType typeChosen = parseChoiceCardType(choice);
            if(typeChosen!=null){
                messageToSend.setType(typeChosen);
                if(deckIsEmpty()) {
                    System.out.println("The deck of the card type you required is empty, please choose another card type. (level|color)");
                } else {
                    if(canBuyCardOfLevel(typeChosen.getLevel())){
                        cardTypeChoiceOK = true;
                    }
                    else{
                        System.out.println("You can't buy a card of this level because you don't have slots to put it on.");
                    }
                }
            } else {
                System.out.println("Card type not available, please choose another one. (level|color)" );
            }
        } while (!cardTypeChoiceOK);
    }

    /**
     * Utility method used to parse from the given choice a valid {@link CardType}.
     * @param choice user's choice.
     * @return a valid {@link CardType} corresponding to the user's choice.
     */
    private CardType parseChoiceCardType(String choice) {
        switch (choice) {
            case "1|blue" : return new CardType(1, ColorCard.blue);
            case "1|yellow" : return new CardType(1, ColorCard.yellow);
            case "1|green" : return new CardType(1, ColorCard.green);
            case "1|purple" : return new CardType(1, ColorCard.purple);
            case "2|blue" : return new CardType(2, ColorCard.blue);
            case "2|yellow" : return new CardType(2, ColorCard.yellow);
            case "2|green" : return new CardType(2, ColorCard.green);
            case "2|purple" : return new CardType(2, ColorCard.purple);
            case "3|blue" : return new CardType(3, ColorCard.blue);
            case "3|yellow" : return new CardType(3, ColorCard.yellow);
            case "3|green" : return new CardType(3, ColorCard.green);
            case "3|purple" : return new CardType(3, ColorCard.purple);
            default: return null;
        }
    }
}
