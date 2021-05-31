package it.polimi.ingsw.Client.clientstates.gui;

import it.polimi.ingsw.Client.clientstates.AbstractUserNameChoice;
import it.polimi.ingsw.Network.Client;

public class UsernameChoiceGUI extends AbstractUserNameChoice {
    @Override
    public void manageUserInteraction() {
        client.sendMessage(messageToSend);
    }

    public UsernameChoiceGUI(Client client,String username) {
        super(client);
        messageToSend.setNickname(username);
    }
}
