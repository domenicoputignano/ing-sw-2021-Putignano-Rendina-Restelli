package it.polimi.ingsw.utils.messages.serverMessages.Updates;

import it.polimi.ingsw.client.reducedmodel.ReducedSoloMode;
import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.serverMessages.ServerMessage;

/**
 * Class instantiated when during a turn, {@link it.polimi.ingsw.model.soloMode.LorenzoIlMagnifico} moves his cross
 * forward on player faith track according to tokens or as result of an action made by player itself.
 */
public class BlackCrossMoveUpdate implements ServerMessage {
    private final int blackCross;
    private final boolean isVaticanReportTriggered;

    public BlackCrossMoveUpdate(int blackCross, boolean isVaticanReportTriggered) {
        this.blackCross = blackCross;
        this.isVaticanReportTriggered = isVaticanReportTriggered;
    }

    /**
     * Method called by client in order to update its reduced model and show changes to player
     * @param handler {@link Client} instance that manages the update itself.
     */
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
