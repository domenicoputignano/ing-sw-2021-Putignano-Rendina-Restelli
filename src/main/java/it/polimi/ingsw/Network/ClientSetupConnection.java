package it.polimi.ingsw.Network;


import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Utils.GameMode;
import it.polimi.ingsw.Utils.Messages.ClientMessages.ConfigurationMessage;
import it.polimi.ingsw.Utils.Messages.ClientMessages.GameModeChoiceMessage;
import it.polimi.ingsw.Utils.Messages.ClientMessages.NumOfPlayerChoiceMessage;
import it.polimi.ingsw.Utils.Messages.ClientMessages.UsernameChoiceMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.*;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientSetupConnection implements Runnable {
    private final Logger LOGGER = Logger.getLogger(ClientSetupConnection.class.getName());
    private final Socket clientSocket;
    private final Server server;
    private String nickname;
    private GameMode mode;
    private int numOfPlayers;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private boolean configurationCompleted = false;


    public ClientSetupConnection(Server server, Socket clientSocket){
        this.server = server;
        this.clientSocket = clientSocket;
    }


    /**
     *
     */
    public void run() {
        try {
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            outputStream.flush();
            inputStream = new ObjectInputStream(clientSocket.getInputStream());
            sendConfigurationMessage(new ServerAsksForNickname());
            clientSocket.setSoTimeout(5000);
            while(!configurationCompleted) {
               Object o = inputStream.readObject();
               if(o instanceof String) {
                   LOGGER.log(Level.INFO, "connected");
               } else {
                   ((ConfigurationMessage)o).handleConfigurationMessage(this);
               }
            }
        } catch (IOException | ClassNotFoundException e) {
            //server.removeNotSetupPlayer(this);
            //LOGGER.log(Level.INFO, "Class not found exception");
            LOGGER.log(Level.INFO, "Client disconnected");
        }
    }


    public void nicknameChoice(UsernameChoiceMessage messageFromClient) throws IOException {
        String nickname;
        boolean availableNickname = false;
        do {
            nickname = messageFromClient.getNickname();
                if (server.isPlayerWithSameNicknamePlaying(nickname)) {
                    sendConfigurationMessage(new NotAvailableNicknameMessage());
                    LOGGER.log(Level.INFO, "Player has chosen an unavailable nickname, connection refused ");
                }
                else {
                    availableNickname = true;
                    this.nickname = nickname;
                    if (server.inactivePlayerAlreadyRegistered(nickname)) {
                        LOGGER.log(Level.INFO, "User " + nickname + " wants to resume game");
                        server.resumeGame(this);
                    } else {
                        server.addWaitingPlayer(this);
                        sendConfigurationMessage(new ServerAskForGameMode(this.nickname));
                    }
                }
            } while (!availableNickname);
        }

    public void numOfPlayersChoice(NumOfPlayerChoiceMessage message) throws IOException {
        numOfPlayers = message.getNumOfPlayers();
        server.notifyLobbyJoin(numOfPlayers,nickname);
        LOGGER.log(Level.INFO, "Client " + nickname + " connected and " + numOfPlayers + " players chosen");
        configurationCompleted = true;
        server.lobby(this);

    }

    public void gameChoice(GameModeChoiceMessage message) throws IOException {
        String choice = message.getGameModeChoice();
        if(choice.equalsIgnoreCase("multiplayer")) {
            mode = GameMode.MULTIPLAYER;
            sendConfigurationMessage(new ServerAskForNumOfPlayer());
        }
        else {
            if(choice.equalsIgnoreCase("solo")){
                mode = GameMode.SOLO;
                server.initializeGame(this);
                configurationCompleted = true;
            }
        }
    }



    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public String getNickname() {
        return nickname;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }


    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public void sendConfigurationMessage(ServerMessage message) throws IOException {
        outputStream.writeObject(message);
        outputStream.flush();
    }


    //Todo eventualmente da togliere, fatto per svuotare le waiting connections dei player che non hanno completato la configurazione
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientSetupConnection that = (ClientSetupConnection) o;
        return Objects.equals(nickname, that.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname);
    }
}


