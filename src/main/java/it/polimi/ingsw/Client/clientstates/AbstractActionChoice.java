package it.polimi.ingsw.Client.clientstates;


import it.polimi.ingsw.Client.clientstates.cli.ActionChoiceCLI;
import it.polimi.ingsw.Network.Client;

public abstract class AbstractActionChoice extends AbstractClientState {


    protected AbstractActionChoice(Client client) {
        super(client);
    }

    @Override
    public ActionChoiceCLI getCLIVersion() {
        return new ActionChoiceCLI(client);
    }


    //TODO
    @Override
    public AbstractClientState getGUIVersion() {
        return null;
    }
}
