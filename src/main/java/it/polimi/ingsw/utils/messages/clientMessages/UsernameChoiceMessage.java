package it.polimi.ingsw.utils.messages.clientMessages;

import it.polimi.ingsw.network.ClientStatus;

import java.io.IOException;

/**
 * Class containing nickname chosen by user.
 */
public class UsernameChoiceMessage implements ConfigurationMessage {
    private String nickname;


    /**
     * Calls right method of {@link ClientStatus} in order to process message itself.
     * @param clientStatus {@link ClientStatus} instance that will process message itself.
     */
    @Override
    public void handleConfigurationMessage(ClientStatus clientStatus) {
        clientStatus.nicknameChoice(this);
    }

    public String getNickname() {
        return nickname;
    }


    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


}
