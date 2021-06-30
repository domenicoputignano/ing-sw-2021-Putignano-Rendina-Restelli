package it.polimi.ingsw.utils.messages.clientMessages;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.commons.CardType;
import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.network.RemoteView;
import it.polimi.ingsw.utils.ResourceSource;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import static it.polimi.ingsw.model.PaymentHandler.areValidInstructions;

/**
 * This class contains information regarding a {@link it.polimi.ingsw.model.BuyDevCard} action.
 */
public class BuyDevCardMessage implements TurnControllerHandleable {
    /**
     * Type of chosen development card.
     */
    private CardType type;
    /**
     * Map containing instruction on how to get resources from player's warehouse.
     */
    private Map<ResourceSource, EnumMap<ResourceType, Integer>> howToTakeResources = new HashMap<>();
    /**
     * Index of the slot where the card will be put.
     */
    private int destinationSlot;


    public CardType getType() {
        return type;
    }

    public Map<ResourceSource, EnumMap<ResourceType, Integer>> getHowToTakeResources() {
        return howToTakeResources;
    }

    public int getDestinationSlot() {
        return destinationSlot;
    }


    /**
     * Method that checks low level correctness of the message.
     * @return true if check is successfully passed.
     */
    public boolean isValidMessage() {
        if(type == null || type.getLevel() <=0 || type.getLevel() > 3 || type.getColor() == null)
            return false;
        if(destinationSlot <= 0 || destinationSlot > 3)
            return false;
        return areValidInstructions(howToTakeResources);
    }

    public void setType(CardType type) {
        this.type = type;
    }


    public void setHowToTakeResources(Map<ResourceSource, EnumMap<ResourceType, Integer>> howToTakeResources) {
        this.howToTakeResources = howToTakeResources;
    }

    public void setDestinationSlot(int destinationSlot) {
        this.destinationSlot = destinationSlot;
    }


    /**
     * Calls right method of turn controller in order to process the message itself and perform the action required by player.
     * @param turnController turn controller instance that process the message.
     * @param sender remote view that forwards the message.
     */
    public void handleMessage(TurnController turnController, RemoteView sender) {
        turnController.handleBuyDevCardMessage(this,sender);
    }

    /**
     * This method is called in order to forward message itself to turn controller.
     * @param gameController game controller instance that will process the message.
     * @param sender remote view that forwards the message.
     */
    public void handleMessage(GameController gameController, RemoteView sender) {
        handleMessage(gameController.getTurnController(), sender);
    }

}
