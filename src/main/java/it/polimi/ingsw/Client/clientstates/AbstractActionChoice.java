package it.polimi.ingsw.Client.clientstates;


import it.polimi.ingsw.Network.Client;

public abstract class AbstractActionChoice extends AbstractClientState {


    protected AbstractActionChoice(Client client) {
        super(client);
    }

    protected boolean normalActionAlreadyDone(){return client.getUI().hasDoneNormalAction();}

}
