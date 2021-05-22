package it.polimi.ingsw.Utils.Messages.ServerMessages;

import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Model.ConclusionEvents.ConclusionEvent;
import it.polimi.ingsw.Network.Client;

public class LastTurnMessage implements ServerMessage{

    private User triggeringUser;
    private ConclusionEvent conclusionEvent;

    public LastTurnMessage(User triggeringUser, ConclusionEvent conclusionEvent) {
        this.triggeringUser = triggeringUser;
        this.conclusionEvent = conclusionEvent;
    }

    @Override
    public void handleMessage(Client handler) {

    }
}
