package it.polimi.ingsw.Client.clientstates;

import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;

public interface AbstractClientStateInterface {
    public void render(ServerMessage message);


}
