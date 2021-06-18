package it.polimi.ingsw.Network;

import it.polimi.ingsw.Client.clientstates.cli.WaitForTurnCLI;
import it.polimi.ingsw.Client.reducedmodel.ReducedGame;
import it.polimi.ingsw.Client.view.CLI;
import it.polimi.ingsw.Client.view.GUI.GUIApp;
import it.polimi.ingsw.Client.view.Gui;
import it.polimi.ingsw.Client.view.UI;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Utils.Messages.ClientMessages.ClientMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.GameResumedMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.GameSetupMessage;
import javafx.application.Application;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    private final String ip;
    private final int port;
    private boolean isActive;
    private ReducedGame game;
    private User user;
    private ObjectInputStream socketInObj ;
    private ObjectOutputStream socketOutObj ;
    private final Logger LOGGER = Logger.getLogger(Client.class.getName());
    private UI ui;
    private Socket socket;
    private ScheduledExecutorService heartbeatSender;


    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;
        this.isActive = true;
    }


    public void start(boolean startAsGui) throws IOException {
        socket = new Socket(ip, port);
        System.out.println("Connection established");
        socketOutObj = new ObjectOutputStream(socket.getOutputStream());
        socketOutObj.flush();
        socketInObj = new ObjectInputStream(socket.getInputStream());
        if(startAsGui) {
            ui = new Gui(this);
            new Thread(GUIApp::launchGUI).start();
        }
        else
            ui = new CLI(this);
        createListeningThread();
    }


    public void sendMessage(ClientMessage message) {
        try {
            synchronized (socketOutObj) {
                socketOutObj.writeObject(message);
                socketOutObj.flush();
            }
            //LOGGER.log(Level.INFO, "Message sent ");
        } catch (IOException e) {
            e.printStackTrace();
            //System.out.println(e.getMessage());
            LOGGER.log(Level.SEVERE, "Error: unable to process messageToSend sending");
        }
    }


    public void setupGame(GameSetupMessage message) {
        game = message.getGame();
    }

    public void setupGame(GameResumedMessage message) {
            if(this.getUser()==null) {
                LOGGER.log(Level.INFO, "Sono il client che si è riconnesso");
                bindUser(message.getSavedUserInstance().getNickname());
                game = message.getGame();
            } else {
                LOGGER.log(Level.INFO, "Sono il client che stava già giocando");
            }
    }

    public void createHeartBeat() throws IOException {
        heartbeatSender = new ScheduledThreadPoolExecutor(1);
        heartbeatSender.scheduleAtFixedRate(() -> {
            synchronized (socketOutObj) {
                try {
                    socketOutObj.writeObject("Connected");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    socketOutObj.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        },0,500, TimeUnit.MILLISECONDS);
    }



    private Thread createListeningThread() {
        Thread t = new Thread(() -> {
            while (isActive) {
                try {
                    ServerMessage message = (ServerMessage) socketInObj.readObject();
                    LOGGER.log(Level.INFO, "Received message from Server of "+message.getClass().getName()+" type");
                    // handle of Received message
                    message.handleMessage(this);
                } catch (IOException e) {
                    isActive = false;
                    LOGGER.log(Level.SEVERE, "Client disconnected!");
                } catch (ClassNotFoundException e) {
                    isActive = false;
                    LOGGER.log(Level.SEVERE, "Error occurred in receiving thread");
                }
            }
        });
        t.start();
        return t;
    }


    public UI getUI() {
        return ui;
    }



    public ReducedGame getGame() {
        return game;
    }

    public void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Impossible to close connection!");
        }
    }


    public void bindUser(String nickname) {
        user = new User(nickname);
    }

    public int getUserPosition() { return game.getPlayer(user).getPosition(); }

    public User getUser() { return user; }
}

