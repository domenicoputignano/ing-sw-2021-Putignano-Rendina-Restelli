package it.polimi.ingsw.Client;

import it.polimi.ingsw.Client.view.CLI;
import it.polimi.ingsw.Client.view.GUI.GUIApp;
import it.polimi.ingsw.Client.view.Gui;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.SoloMode.SoloMode;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ClientMessages.ClientMessage;
import it.polimi.ingsw.Utils.Messages.ClientMessages.GameControllerHandleable;

import java.util.logging.Level;

public class OfflineClient extends Client {

    private OfflineRemoteView clientView;

    @Override
    public void start(boolean startAsGui) {
        this.user = new User("Guest");
        SoloMode soloModeGame = new SoloMode(new Player(this.user.getNickname()));
        this.game = soloModeGame.getReducedVersion();
        GameController gameController = new GameController(soloModeGame);
        clientView = new OfflineRemoteView(user,gameController,this);
        if(startAsGui) {
            ui = new Gui(this);
            ui.setSoloMode(true);
            new Thread(GUIApp::launchGUI).start();
            try {
                GUIApp.waitForGameSetup(this);
            } catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, "Error detected in application launch");
            }
        }
        else
            ui = new CLI(this);
        soloModeGame.notifyGameSetup();
    }

    @Override
    public void sendMessage(ClientMessage message) {
        clientView.handleClientMessage((GameControllerHandleable) message);
    }
}
