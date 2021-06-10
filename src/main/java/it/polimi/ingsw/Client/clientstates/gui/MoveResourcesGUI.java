package it.polimi.ingsw.Client.clientstates.gui;

import it.polimi.ingsw.Client.clientstates.AbstractMoveResources;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ClientMessages.MoveResourcesMessage;

public class MoveResourcesGUI extends AbstractMoveResources {

    public MoveResourcesGUI(Client client) {
        super(client);
    }

    @Override
    public void manageUserInteraction() {
        this.client.sendMessage(messageToSend);
    }

    public void setMessage(MoveResourcesMessage message)
    {
        this.messageToSend = message;
    }
}
