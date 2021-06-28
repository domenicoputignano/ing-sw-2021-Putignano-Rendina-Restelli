package it.polimi.ingsw.utils.messages.serverMessages;

import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.model.gameEvents.ConclusionEvent;
import it.polimi.ingsw.network.Client;

public class LastTurnMessage implements ServerMessage{

    private User triggeringUser;
    private ConclusionEvent conclusionEvent;

    public LastTurnMessage(User triggeringUser, ConclusionEvent conclusionEvent) {
        this.triggeringUser = triggeringUser;
        this.conclusionEvent = conclusionEvent;
    }

    @Override
    public void handleMessage(Client handler) {
        handler.getUI().render(this);
    }

    public User getTriggeringUser() {
        return triggeringUser;
    }

    public ConclusionEvent getConclusionEvent() {
        return conclusionEvent;
    }
}
