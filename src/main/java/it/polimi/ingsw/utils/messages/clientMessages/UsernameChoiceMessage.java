package it.polimi.ingsw.utils.messages.clientMessages;

import it.polimi.ingsw.network.ClientStatus;

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
