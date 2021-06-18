package it.polimi.ingsw.Utils.Messages.ClientMessages;

import it.polimi.ingsw.Network.ClientStatus;

import java.io.IOException;

public class UsernameChoiceMessage implements ConfigurationMessage {
    private String nickname;


    @Override
    public void handleConfigurationMessage(ClientStatus clientStatus) throws IOException {
        clientStatus.nicknameChoice(this);
    }

    public String getNickname() {
        return nickname;
    }


    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


}
