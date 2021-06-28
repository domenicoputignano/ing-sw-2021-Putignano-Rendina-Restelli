package it.polimi.ingsw.client.clientstates.gui;

import it.polimi.ingsw.client.clientstates.AbstractUserNameChoice;
import it.polimi.ingsw.network.Client;

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
