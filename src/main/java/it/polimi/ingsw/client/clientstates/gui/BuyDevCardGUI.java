package it.polimi.ingsw.client.clientstates.gui;

import it.polimi.ingsw.client.clientstates.AbstractBuyDevCard;
import it.polimi.ingsw.commons.CardType;
import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.ResourceSource;

import java.util.EnumMap;
import java.util.Map;

public class BuyDevCardGUI extends AbstractBuyDevCard {

    public BuyDevCardGUI(Client client) {
        super(client);
    }

    @Override
    public void manageUserInteraction() {
        client.sendMessage(messageToSend);
    }

    public void compileMessage(CardType type, Map<ResourceSource, EnumMap<ResourceType,Integer>> paymentInstruction, int slot) {
        messageToSend.setType(type);
        messageToSend.setHowToTakeResources(paymentInstruction);
        messageToSend.setDestinationSlot(slot);
    }
}
