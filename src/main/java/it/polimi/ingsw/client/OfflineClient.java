package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.client.view.GUI.GUIApp;
import it.polimi.ingsw.client.view.Gui;
import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.soloMode.SoloMode;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.clientMessages.ClientMessage;
import it.polimi.ingsw.utils.messages.clientMessages.GameControllerHandleable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

public class OfflineClient extends Client {

    private OfflineRemoteView clientView;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

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
        else {
            ui = new CLI(this);
            ui.setSoloMode(true);
        }
        soloModeGame.notifyGameSetup();
    }

    @Override
    public void sendMessage(ClientMessage message) {
        executor.submit(() -> clientView.handleClientMessage((GameControllerHandleable) message));
    }
}
