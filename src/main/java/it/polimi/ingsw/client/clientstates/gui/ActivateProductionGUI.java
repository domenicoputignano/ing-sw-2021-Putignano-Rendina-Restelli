package it.polimi.ingsw.client.clientstates.gui;

import it.polimi.ingsw.client.clientstates.AbstractActivateProduction;
import it.polimi.ingsw.network.Client;

public class ActivateProductionGUI extends AbstractActivateProduction {

    @Override
    public void manageUserInteraction() {
        client.sendMessage(messageToSend);
    }

    public ActivateProductionGUI(Client client) {
        super(client);
    }
}
