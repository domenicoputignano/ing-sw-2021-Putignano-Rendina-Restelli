package it.polimi.ingsw.Network;


import it.polimi.ingsw.Utils.GameMode;
import it.polimi.ingsw.Utils.Messages.ClientMessages.*;
import it.polimi.ingsw.Utils.Messages.ServerMessages.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private ConnectionStates connectionState;
    private final Logger LOGGER = Logger.getLogger(ClientStatus.class.getName());


    public ClientStatus(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        this.isActive = true;
        this.connectionState = ConnectionStates.CONFIGURATION;
    }



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



    public void run(){
        try {
            outputStreamToClient = new ObjectOutputStream(socket.getOutputStream());
            outputStreamToClient.flush();
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


    public void bindRemoteView(NetworkRemoteView remoteView) {
        this.remoteView = remoteView;
        this.connectionState = ConnectionStates.INGAME;
    }

    public boolean isActive() {
        return isActive;
    }

    public NetworkRemoteView getRemoteView() { return remoteView; }


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

    public void numOfPlayersChoice(NumOfPlayerChoiceMessage message) {
        numOfPlayers = message.getNumOfPlayers();
        server.notifyLobbyJoin(numOfPlayers,nickname);
        LOGGER.log(Level.INFO, "Client " + nickname + " connected and " + numOfPlayers + " players chosen");
        server.lobby(this);

    }

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

    public String getNickname() {
        return nickname;
    }

    public GameMode getGameMode() {
        return mode;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }
}
