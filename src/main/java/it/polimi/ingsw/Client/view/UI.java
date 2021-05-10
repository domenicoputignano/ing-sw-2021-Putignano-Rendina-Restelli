package it.polimi.ingsw.Client.view;

import it.polimi.ingsw.Client.clientstates.AbstractClientStateInterface;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.ActivateProductionUpdate;


public abstract class UI {
    protected Client client;
    protected AbstractClientStateInterface clientState;

    public abstract void showUpdate(ActivateProductionUpdate message);

}
