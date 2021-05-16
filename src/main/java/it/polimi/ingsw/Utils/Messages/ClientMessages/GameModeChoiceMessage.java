package it.polimi.ingsw.Utils.Messages.ClientMessages;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.RemoteView;

public class GameModeChoiceMessage implements ClientMessage {

    private final String gameModeChoice;

    public GameModeChoiceMessage(String gameModeChoice) {
        this.gameModeChoice = gameModeChoice;
    }

    @Override
    public void handleMessage(GameController gameController, RemoteView sender) {}

    @Override
    public boolean isValidMessage() {
        return true;
    }

    public String getGameModeChoice() {
        return gameModeChoice;
    }
}
