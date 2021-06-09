package it.polimi.ingsw.Utils.Messages.ServerMessages.Updates;

import it.polimi.ingsw.Client.reducedmodel.ReducedSoloMode;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;

public class BlackCrossMoveUpdate implements ServerMessage {
    private final int blackCross;

    public BlackCrossMoveUpdate(int blackCross) {
        this.blackCross = blackCross;
    }

    @Override
    public void handleMessage(Client handler) {
        ((ReducedSoloMode)handler.getGame()).performUpdate(this);
    }

    public int getBlackCross() {
        return blackCross;
    }
}
