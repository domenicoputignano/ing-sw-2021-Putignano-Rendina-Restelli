package it.polimi.ingsw.Utils.Messages.ClientMessages;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.ClientSetupConnection;
import it.polimi.ingsw.Network.RemoteView;

import java.io.IOException;

public class UsernameChoiceMessage implements ConfigurationMessage {
    private String nickname;


    @Override
    public void handleConfigurationMessage(ClientSetupConnection connection) throws IOException {
        connection.nicknameChoice(this);
    }

    public String getNickname() {
        return nickname;
    }


    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


}
