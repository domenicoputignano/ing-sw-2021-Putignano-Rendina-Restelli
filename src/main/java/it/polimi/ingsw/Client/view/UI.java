package it.polimi.ingsw.Client.view;

import it.polimi.ingsw.Client.clientstates.AbstractClientState;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.ActivateProductionUpdate;


public abstract class UI {
    protected Client client;
    protected AbstractClientState clientState;

    protected UI(Client client) {
        this.client = client;
    }

    public abstract void showUpdate(ActivateProductionUpdate message);
    public abstract void setClientState(AbstractClientState clientState);

}
