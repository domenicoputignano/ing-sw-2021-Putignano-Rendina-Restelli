package it.polimi.ingsw.utils.messages.serverMessages.Updates;

import it.polimi.ingsw.client.reducedmodel.ReducedSoloMode;
import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.serverMessages.ServerMessage;


public class BlackCrossMoveUpdate implements ServerMessage {
    private final int blackCross;
    private final boolean isVaticanReportTriggered;

    public BlackCrossMoveUpdate(int blackCross, boolean isVaticanReportTriggered) {
        this.blackCross = blackCross;
        this.isVaticanReportTriggered = isVaticanReportTriggered;
    }

    @Override
    public void handleMessage(Client handler) {
        if(handler.getUI().isCLI()) {
            ((CLI)handler.getUI()).render(this);
        }
        ((ReducedSoloMode)handler.getGame()).performUpdate(this);
    }

    public int getBlackCross() {
        return blackCross;
    }

    public boolean isVaticanReportTriggered() {
        return isVaticanReportTriggered;
    }
}
