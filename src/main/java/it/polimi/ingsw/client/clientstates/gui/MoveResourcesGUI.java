package it.polimi.ingsw.client.clientstates.gui;

import it.polimi.ingsw.client.clientstates.AbstractMoveResources;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.clientMessages.MoveResourcesMessage;

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
