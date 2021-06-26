package it.polimi.ingsw.Network;


import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Utils.GameMode;
import it.polimi.ingsw.Utils.Messages.ClientMessages.*;
import it.polimi.ingsw.Utils.Messages.ServerMessages.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * This is a Runnable class that is instantiated whenever a client connects to the server and
 * it holds the communication until a disconnection occurs.
 * It handles configuration messages and delegates game messages to remote view.
 */
public class ClientStatus implements Runnable {

    private Server server;
    private NetworkRemoteView remoteView;
    private boolean isActive;
    private ObjectOutputStream outputStreamToClient;
    private ObjectInputStream inputFromClient;
    private final Socket socket;
    private String nickname;
    private GameMode mode;
    private int numOfPlayers;
    /**
     * State of connection that allows to know which kind of message is to expect
     */
    private ConnectionStates connectionState;
    private final Logger LOGGER = Logger.getLogger(ClientStatus.class.getName());


    /**
     * Initializes an active ClientStatus
     * @param socket socket that identifies the client that has to be handled by this instance
     * @param server server to which it belongs.
     */
    public ClientStatus(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        this.isActive = true;
        this.connectionState = ConnectionStates.CONFIGURATION;
    }


    /**
     * Synchronized method called by remote view to send message to the client.
     * @param serverMessage an instance of a server message to be sent.
     */
    public synchronized void send(ServerMessage serverMessage) {
        try {
            outputStreamToClient.reset();
            outputStreamToClient.writeObject(serverMessage);
            outputStreamToClient.flush();
        } catch (IOException e) {
            e.printStackTrace(System.out);
            //TODO modificare come se trovassimo una disconnessione
            LOGGER.log(Level.SEVERE, "Disconnection detected while sending a message");
            isActive = false;
        }
    }


    /**
     * Method that runs until the connection is open.
     * It receives all the messages sent from remote host and forwards them to remote view, if required.
     * According to the state of connection, it handles {@link SocketTimeoutException} and {@link IOException}
     * if the connection with the remote host goes lost.
     */
    public void run(){
        try {
            outputStreamToClient = new ObjectOutputStream(socket.getOutputStream());
            outputStreamToClient.flush(); //This is required otherwise following instantiation of ObjectInputStream will block forever
            inputFromClient = new ObjectInputStream(socket.getInputStream());
            send(new ServerAsksForNickname());
            while(isActive) {
                Object message = inputFromClient.readObject();
                if(message instanceof PingMessage) {
                    server.reply(() -> {
                        send(new PongMessage());
                    });
                }
                else {
                    //LOGGER.log(Level.INFO, ""+message.getClass().getName());
                    try {
                        if (connectionState == ConnectionStates.CONFIGURATION) {
                            ((ConfigurationMessage)message).handleConfigurationMessage(this);
                        }
                        else if (connectionState == ConnectionStates.INGAME) {
                            GameControllerHandleable messageFromClient = (GameControllerHandleable) message;
                            remoteView.handleClientMessage(messageFromClient);
                            LOGGER.log(Level.INFO, "Message from client of type "+messageFromClient.getClass().getName());
                        }
                    } catch (ClassCastException e) {
                        LOGGER.log(Level.INFO, ""+message.getClass().getName());
                        LOGGER.log(Level.INFO, "Wrong type message");
                    }
                }
            }
        } catch (SocketTimeoutException e) {
            if(this.connectionState == ConnectionStates.INGAME) {
                isActive = false;
                this.connectionState = ConnectionStates.DISCONNECTED;
                remoteView.handlePlayerDisconnection();
                LOGGER.log(Level.SEVERE, "Client disconnected while playing a match");
            }
            if(this.connectionState == ConnectionStates.CONFIGURATION) {
                isActive = false;
                this.connectionState = ConnectionStates.DISCONNECTED;
                server.removeNotSetupPlayer(this);
                LOGGER.log(Level.INFO, "Client disconnected while waiting for a game to start");
            }
        }
        catch (IOException e) {
            if(this.connectionState == ConnectionStates.INGAME) {
                isActive = false;
                this.connectionState = ConnectionStates.DISCONNECTED;
                remoteView.handlePlayerDisconnection();
                LOGGER.log(Level.SEVERE, "Client disconnected while playing a match");
            }
            if(this.connectionState == ConnectionStates.CONFIGURATION) {
                isActive = false;
                this.connectionState = ConnectionStates.DISCONNECTED;
                server.removeNotSetupPlayer(this);
                LOGGER.log(Level.INFO, "Client disconnected while waiting for a game to start");
            }
        }
        catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error in receiving message from client");
        }
    }


    /**
     * This method is used when a game has been initialized by Server. When a remote host connects
     * it hasn't any game instance yet, so after game creation a {@link NetworkRemoteView} is instantiated in the ClientStatus.
     */
    public void bindRemoteView(NetworkRemoteView remoteView) {
        this.remoteView = remoteView;
        this.connectionState = ConnectionStates.INGAME;
    }


    /**
     * It handles the first step in client configuration. It ensures that a valid nickname is chosen
     * and if required, it allows player reconnection.
     * @param messageFromClient message containing nickname chosen by remote host.
     */
    public void nicknameChoice(UsernameChoiceMessage messageFromClient) {
        String nickname;
        nickname = messageFromClient.getNickname();
        if (server.isPlayerWithSameNicknamePlaying(nickname)) {
            send(new NotAvailableNicknameMessage());
            LOGGER.log(Level.INFO, "Player has chosen an unavailable nickname, connection refused ");
        }
        else {
            this.nickname = nickname;
            if (server.inactivePlayerAlreadyRegistered(nickname)) {
                LOGGER.log(Level.INFO, "User " + nickname + " wants to resume game");
                server.resumeGame(this);
            } else {
                server.addWaitingPlayer(this);
                server.registerClientStatus(nickname,this);
                send(new ServerAskForGameMode(this.nickname));
            }
        }
    }


    /**
     * It handles game mode choice.
     * In case of multiplayer mode, it will ask the client for number of players required.
     * @param message message containing game mode.
     */
    public void gameChoice(GameModeChoiceMessage message) {
        String choice = message.getGameModeChoice();
        if(choice.equalsIgnoreCase("multiplayer")) {
            mode = GameMode.MULTIPLAYER;
            send(new ServerAskForNumOfPlayer());
        }
        else {
            if(choice.equalsIgnoreCase("solo")){
                mode = GameMode.SOLO;
                server.initializeGame(this);
            }
        }
    }

    /**
     * This method handles number of players selection end, at the end, adds itself to server lobby.
     * @param message message containing number of players selected if a multiplayer mode has been chosen.
     */
    public void numOfPlayersChoice(NumOfPlayerChoiceMessage message) {
        numOfPlayers = message.getNumOfPlayers();
        server.notifyLobbyJoin(numOfPlayers,nickname);
        LOGGER.log(Level.INFO, "Client " + nickname + " connected and " + numOfPlayers + " players chosen");
        server.lobby(this);
    }

    /**
     * It closes communication with remote view after that a disconnection has been detected.
     */
    public void closeConnection() {
        try {
            remoteView = null;
            isActive = false;
            outputStreamToClient.close();
            inputFromClient.close();
            socket.close();
        } catch (IOException e) {
            LOGGER.log(Level.INFO, "Unable to close connection");
        }
    }


    /**
     * This method is triggered from {@link NetworkRemoteView} and it is called in order to clear a match that has been paused
     * for a certain time after all player disconnection.
     * @param usersInAPausedMatch list of users that the server has to delete.
     */
    public void deleteMatch(List<User> usersInAPausedMatch) {
        server.deleteMatch(usersInAPausedMatch);
    }

    public boolean isActive() {
        return isActive;
    }

    public NetworkRemoteView getRemoteView() { return remoteView; }

    public String getNickname() {
        return nickname;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }
}
