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

/**
 * This class represents the offline implementation of the main class client-side.
 * It permits to a client to run the game locally without connecting to the server.
 * It receives messages built through user's interaction with ui from the Client classes and
 * forwards them to its {@link OfflineRemoteView} which handles it.
 */
public class OfflineClient extends Client {

    /**
     * The remote view which handles the messages sent from this class.
     */
    private OfflineRemoteView clientView;
    /**
     * The single thread used to send messages to the remote view.
     */
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    /**
     * Main method that starts the client, its user interface, and also the model and the controller.
     * @param startAsGui variable that establishes if user wants to play with GUI
     */
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
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, "Error detected in application launch");
                Thread.currentThread().interrupt();
            }
        }
        else {
            ui = new CLI(this);
            ui.setSoloMode(true);
        }
        soloModeGame.notifyGameSetup();
    }

    /**
     * Sends a client message to the local remote view which handles it by forwarding it to the controller.
     * @param message the message to send.
     */
    @Override
    public void sendMessage(ClientMessage message) {
        executor.submit(() -> clientView.handleClientMessage((GameControllerHandleable) message));
    }
}
