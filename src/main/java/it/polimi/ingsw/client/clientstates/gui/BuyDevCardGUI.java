package it.polimi.ingsw.client.clientstates.gui;

import it.polimi.ingsw.client.clientstates.AbstractBuyDevCard;
import it.polimi.ingsw.commons.CardType;
import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.ResourceSource;

import java.util.EnumMap;
import java.util.Map;

/**
 * This class represents the GUI-specific state that is reached when the player in turn
 * wants to buy a {@link it.polimi.ingsw.commons.DevelopmentCard} during his turn.
 */
public class BuyDevCardGUI extends AbstractBuyDevCard {

    /**
     * Initializes references to client.
     */
    public BuyDevCardGUI(Client client) {
        super(client);
    }

    /**
     * Main method of the class used to send the message to the server after the user has chosen how to perform this
     * action using the JavaFX GUI.
     */
    @Override
    public void manageUserInteraction() {
        client.sendMessage(messageToSend);
    }

    /**
     * Method used to compile the message to send to the server with the given arguments
     * @param type the card type of the card the user wants to buy.
     * @param paymentInstruction where to take the resources needed to buy the card.
     * @param slot the slot the user wants to place the card on.
     */
    public void compileMessage(CardType type, Map<ResourceSource, EnumMap<ResourceType,Integer>> paymentInstruction, int slot) {
        messageToSend.setType(type);
        messageToSend.setHowToTakeResources(paymentInstruction);
        messageToSend.setDestinationSlot(slot);
    }
}
