package it.polimi.ingsw.Client.clientstates.cli;

import it.polimi.ingsw.Client.clientstates.AbstractBuyDevCard;
import it.polimi.ingsw.Commons.CardType;
import it.polimi.ingsw.Commons.ColorCard;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;

import java.util.Scanner;

public class BuyDevCardCLI extends AbstractBuyDevCard {
    Scanner input = new Scanner(System.in);

    @Override
    public void render(ServerMessage message) {

    }

    @Override
    public void manageUserInteraction() {
        boolean doneSelection = false;
        do {
            chooseCardType();
            if(checkCostRequiredCardType(takeDeckFromCardType(message.getType()))) {
                doneSelection = true;
            } else {
                System.out.println("You don't have enough resources to buy this card! Choose another one.");
            }
        } while(!doneSelection);
        doneSelection = false;
        chooseSlotDestination();
    }

    private void chooseSlotDestination() {
        System.out.println("Which slot do you want to put the card on? [1-3]");
        int choice;
        boolean slotDestinationChoiceOK = false;
        do {
            choice = input.nextInt();
            if(choice==1||choice==2||choice==3){
                if(canActivateCardOnThisSlot(choice)) {
                    slotDestinationChoiceOK = true;
                    message.setDestinationSlot(choice);
                } else {
                    System.out.println("You can't activate the card on this slot, choose another slot. [1-3]");
                }
            } else {
                System.out.println("Invalid choice, which slot do you want to put the card on? [1-3]");
            }
        } while (!slotDestinationChoiceOK);
    }

    private void chooseCardType() {
        System.out.println("Which type of card do you want to buy? (level|color)");
        String choice;
        boolean cardTypeChoiceOK = false;
        do {
            choice = input.nextLine();
            CardType typeChosen = parseChoiceCardType(choice);
            if(typeChosen!=null){
                message.setType(typeChosen);
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
