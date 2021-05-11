package it.polimi.ingsw.Utils.Messages.ClientMessages;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Controller.TurnController;
import it.polimi.ingsw.Commons.CardType;
import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Network.RemoteView;
import it.polimi.ingsw.Utils.ResourceSource;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import static it.polimi.ingsw.Model.PaymentHandler.areValidInstructions;

public class BuyDevCardMessage implements TurnControllerHandleable {
    private CardType type;
    private Map<ResourceSource, EnumMap<ResourceType, Integer>> howToTakeResources = new HashMap<>();
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

    //Da implementare in ciascuna classe messaggio
    public boolean isValidMessage()
    {
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


    public void handleMessage(TurnController turnController, RemoteView sender) {
        turnController.handleBuyDevCardMessage(this,sender);
    }

    public void handleMessage(GameController gameController, RemoteView sender) {
        handleMessage(gameController.getTurnController(), sender);
    }

}
