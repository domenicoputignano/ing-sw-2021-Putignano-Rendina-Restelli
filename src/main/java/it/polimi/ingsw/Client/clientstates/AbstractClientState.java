package it.polimi.ingsw.Client.clientstates;

import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;

public abstract class AbstractClientState {

    public abstract void render(ServerMessage message);
    public abstract void manageUserInteraction();

}
