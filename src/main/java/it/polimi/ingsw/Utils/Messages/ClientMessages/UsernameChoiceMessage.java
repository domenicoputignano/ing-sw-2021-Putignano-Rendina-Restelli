package it.polimi.ingsw.Utils.Messages.ClientMessages;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.RemoteView;

public class UsernameChoiceMessage implements ClientMessage {
    private String nickname;


    public String getNickname() {
        return nickname;
    }

    @Override
    public void handleMessage(GameController gameController, RemoteView sender) {
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public boolean isValidMessage() {
        return true;
    }
}
