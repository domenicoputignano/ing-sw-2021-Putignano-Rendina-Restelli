package it.polimi.ingsw.Client.clientstates.gui;

import it.polimi.ingsw.Client.clientstates.AbstractBuyDevCard;
import it.polimi.ingsw.Commons.CardType;
import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.ResourceSource;

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
