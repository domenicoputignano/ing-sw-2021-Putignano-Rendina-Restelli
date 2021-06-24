package it.polimi.ingsw.Network;

import it.polimi.ingsw.Client.reducedmodel.ReducedGame;
import it.polimi.ingsw.Client.view.CLI;
import it.polimi.ingsw.Client.view.GUI.GUIApp;
import it.polimi.ingsw.Client.view.Gui;
import it.polimi.ingsw.Client.view.UI;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Utils.Messages.ClientMessages.ClientMessage;
import it.polimi.ingsw.Utils.Messages.ClientMessages.PingMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.GameResumedMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.GameSetupMessage;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Client {


    protected ReducedGame game;
    protected User user;
    protected final Logger LOGGER = Logger.getLogger(Client.class.getName());
    protected UI ui;

    public abstract void start(boolean startAsGui);

    public abstract void sendMessage(ClientMessage message);


    public void setupGame(GameSetupMessage message) {
        game = message.getGame();
    }

    public void setupGame(GameResumedMessage message) {
            if(this.getUser()==null) {
                LOGGER.log(Level.INFO, "Sono il client che si è riconnesso");
                bindUser(message.getSavedUserInstance().getNickname());
                game = message.getGame();
                ui.setSoloMode(game.isSoloMode());
            } else {
                LOGGER.log(Level.INFO, "Sono il client che stava già giocando");
            }
    }


    public UI getUI() {
        return ui;
    }

    public ReducedGame getGame() {
        return game;
    }

    public void bindUser(String nickname) {
        user = new User(nickname);
    }

    public int getUserPosition() { return game.getPlayer(user).getPosition(); }

    public User getUser() { return user; }
}

