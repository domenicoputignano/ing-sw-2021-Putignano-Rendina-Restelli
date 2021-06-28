package it.polimi.ingsw.network;

import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.client.view.GUI.GUIApp;
import it.polimi.ingsw.client.view.Gui;
import it.polimi.ingsw.utils.messages.clientMessages.ClientMessage;
import it.polimi.ingsw.utils.messages.clientMessages.PingMessage;
import it.polimi.ingsw.utils.messages.serverMessages.ServerMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * Since whole project is based on distributed MVC architectural pattern, this class has what is needed to exchange
 * information with the server, especially it is the door through which the player sends messages and
 * receives errors or updates.
 */
public class NetworkClient extends Client {

    private final String ip;
    private final int port;
    private boolean isActive;
    private ObjectInputStream socketInObj ;
    private ObjectOutputStream socketOutObj ;
    private Socket socket;
    /**
     * Executor that periodically pings server
     */
    private ScheduledExecutorService heartbeatSender;

    /**
     * Initializes a network client through assigning it Server IP address and its port.
     * @param ip Server ip address
     * @param port Server port.
     */
    public NetworkClient(String ip, int port){
        this.ip = ip;
        this.port = port;
        this.isActive = true;
    }


    /**
     * Main method that starts the client, its user interface, a thread for inbound messages
     * and one for ping sending.
     * @param startAsGui variable that establishes if user wants to play with GUI.
     */
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
            return;
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

    /**
     * Method that sends a {@link ClientMessage} through the socket.
     * @param message message that has to be sent.
     */
    public void sendMessage(ClientMessage message) {
        try {
            synchronized (socketOutObj) {
                socketOutObj.writeObject(message);
                socketOutObj.flush();
            }
            //LOGGER.log(Level.INFO, "Message sent ");
        } catch (IOException e) {
            //System.out.println(e.getMessage());
            LOGGER.log(Level.SEVERE, "Error: unable to process message sending");
        }
    }

    /**
     * Initializes ping sender that has to ensure a stable TCP connection.
     */
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

    /**
     * Initializes a thread that will wait for an inbound messages over the socket.
     */
    private void createListeningThread() {
        Thread t = new Thread(() -> {
            while (isActive) {
                try {
                    ServerMessage message = (ServerMessage) socketInObj.readObject();
                    //LOGGER.log(Level.INFO, "Received message from Server of "+message.getClass().getName()+" type");
                    //handle of Received message
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
    }

    /**
     * Closes the connection with remote host.
     */
    public void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Impossible to close connection!");
        }
    }


}
