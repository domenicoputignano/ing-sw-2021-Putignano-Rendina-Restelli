package it.polimi.ingsw.Client.view;

import it.polimi.ingsw.Client.clientstates.AbstractClientState;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.ActivateProductionUpdate;


public abstract class UI {
    protected Client client;
    protected AbstractClientState clientState;

    public abstract void showUpdate(ActivateProductionUpdate message);


    //TODO cambiare, messa solo per esigenze di test
    public void setClient(Client client) {
        this.client = client;
    }
}
