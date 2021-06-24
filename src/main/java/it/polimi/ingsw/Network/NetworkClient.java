package it.polimi.ingsw.Network;

import it.polimi.ingsw.Client.view.CLI;
import it.polimi.ingsw.Client.view.GUI.GUIApp;
import it.polimi.ingsw.Client.view.Gui;
import it.polimi.ingsw.Utils.Messages.ClientMessages.ClientMessage;
import it.polimi.ingsw.Utils.Messages.ClientMessages.PingMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class NetworkClient extends Client {

    private final String ip;
    private final int port;
    private boolean isActive;
    private ObjectInputStream socketInObj ;
    private ObjectOutputStream socketOutObj ;
    private Socket socket;
    private ScheduledExecutorService heartbeatSender;

    public NetworkClient(String ip, int port){
        this.ip = ip;
        this.port = port;
        this.isActive = true;
    }


    public void start(boolean startAsGui) {
        try {
            socket = new Socket(ip, port);
        System.out.println("Connection established");
        socketOutObj = new ObjectOutputStream(socket.getOutputStream());
        socketOutObj.flush();
        socketInObj = new ObjectInputStream(socket.getInputStream());
        socket.setSoTimeout(5000);
        if(startAsGui) {
            ui = new Gui(this);
            new Thread(GUIApp::launchGUI).start();
            }
        else
            ui = new CLI(this);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,"Unable to start the client");
        }
        createListeningThread();
        createHeartBeat();
    }



    private void sendObject(Object o) throws IOException {
        synchronized (socketOutObj) {
            socketOutObj.writeObject(o);
            socketOutObj.flush();
        }
    }

    public void sendMessage(ClientMessage message) {
        try {
            synchronized (socketOutObj) {
                socketOutObj.writeObject(message);
                socketOutObj.flush();
            }
            //LOGGER.log(Level.INFO, "Message sent ");
        } catch (IOException e) {
            //System.out.println(e.getMessage());
            LOGGER.log(Level.SEVERE, "Error: unable to process messageToSend sending");
        }
    }

    public void createHeartBeat() {
        heartbeatSender = new ScheduledThreadPoolExecutor(1);
        heartbeatSender.scheduleAtFixedRate(() -> {
            try {
                sendObject(new PingMessage());
            } catch (IOException e) {
                LOGGER.log(Level.INFO, "Server unreachable");
                this.isActive = false;
                heartbeatSender.shutdown();
            }
        },0,500, TimeUnit.MILLISECONDS);
    }


    private Thread createListeningThread() {
        Thread t = new Thread(() -> {
            while (isActive) {
                try {
                    ServerMessage message = (ServerMessage) socketInObj.readObject();
                    //LOGGER.log(Level.INFO, "Received message from Server of "+message.getClass().getName()+" type");
                    // handle of Received message
                    message.handleMessage(this);
                } catch (IOException e) {
                    isActive = false;
                    LOGGER.log(Level.SEVERE, "Client disconnected!");
                    closeConnection();
                } catch (ClassNotFoundException e) {
                    isActive = false;
                    LOGGER.log(Level.SEVERE, "Error occurred in receiving thread");
                }
            }
        });
        t.start();
        return t;
    }

    public void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Impossible to close connection!");
        }
    }


}
