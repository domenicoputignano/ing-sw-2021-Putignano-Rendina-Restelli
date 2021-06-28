package it.polimi.ingsw.Utils.Messages.ServerMessages;

import it.polimi.ingsw.Client.CliStatesController;
import it.polimi.ingsw.Network.Client;

public class NotAvailableNicknameMessage implements ServerMessage {

    @Override
    public void handleMessage(Client handler) {
        handler.getUI().render(this);
        CliStatesController.updateCliState(new ServerAsksForNickname(), handler.getUI());
    }

}
