package it.polimi.ingsw.Client.clientstates.gui;

import it.polimi.ingsw.Client.clientstates.AbstractActivateProduction;
import it.polimi.ingsw.Network.Client;

public class ActivateProductionGUI extends AbstractActivateProduction {

    @Override
    public void manageUserInteraction() {
        client.sendMessage(messageToSend);
    }

    public ActivateProductionGUI(Client client) {
        super(client);
    }
}
