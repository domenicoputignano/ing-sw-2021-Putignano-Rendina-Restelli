package it.polimi.ingsw.Utils.Messages.ServerMessages.Updates;

import it.polimi.ingsw.Client.reducedmodel.ReducedSoloMode;
import it.polimi.ingsw.Client.view.CLI;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;


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
