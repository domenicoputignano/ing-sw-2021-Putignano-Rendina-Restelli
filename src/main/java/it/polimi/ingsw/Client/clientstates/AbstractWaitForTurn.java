package it.polimi.ingsw.Client.clientstates;

import it.polimi.ingsw.Network.Client;

public abstract class AbstractWaitForTurn extends AbstractClientState {

    protected AbstractWaitForTurn(Client client) { super(client); }
    protected boolean inTurn = false;


    public void setInTurn() {
        inTurn = true;
    }


}
