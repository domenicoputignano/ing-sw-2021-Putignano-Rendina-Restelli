package it.polimi.ingsw.Network;


import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Utils.GameMode;
import it.polimi.ingsw.Utils.Messages.ClientMessages.GameModeChoiceMessage;
import it.polimi.ingsw.Utils.Messages.ClientMessages.NumOfPlayerChoiceMessage;
import it.polimi.ingsw.Utils.Messages.ClientMessages.UsernameChoiceMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.*;

import java.io.*;
import java.net.Socket;
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

    public ClientSetupConnection(Server server, Socket clientSocket){
        this.server = server;
        this.clientSocket = clientSocket;
    }


    /*Method to initialize numOfPlayer and nickName*/
    public void run() {
        try {
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            outputStream.flush();
            inputStream = new ObjectInputStream(clientSocket.getInputStream());
            nicknameChoice();
            if (server.inactivePlayerAlreadyRegistered(nickname)) {
                LOGGER.log(Level.INFO, "User "+nickname+" wants to resume game");
                //TODO correctly resuming of the game. Check what happens if player in turn disconnects
                server.resumeGame(this);
            }
            else {
                gameChoice();
                if(mode == GameMode.SOLO) {
                    server.initializeGame(this);
                }
                else {
                    numOfPlayersChoice();
                    server.notifyLobbyJoin(numOfPlayers,nickname);
                    LOGGER.log(Level.INFO, "Client " + nickname + " connected and " + numOfPlayers + " players chosen");
                    server.lobby(this);
                }

            }
        } catch (IOException | ClassNotFoundException e) {
            server.removeNotSetupPlayer(this);
            LOGGER.log(Level.INFO, "Reset connection");
        }
    }


    private void nicknameChoice() throws IOException, ClassNotFoundException {
        String nickname;
        boolean availableNickname = false;
        sendConfigurationMessage(new ServerAsksForNickname());
        do {
            UsernameChoiceMessage messageFromClient = (UsernameChoiceMessage) inputStream.readObject();
            nickname = messageFromClient.getNickname();
                if (server.isPlayerWithSameNicknamePlaying(nickname)) {
                    sendConfigurationMessage(new NotAvailableNicknameMessage());
                    LOGGER.log(Level.INFO, "Player has chosen an unavailable nickname, connection refused ");
                }
                else {
                    availableNickname = true;
                }
            }
            while (!availableNickname);
        this.nickname = nickname;
        server.addWaitingPlayer(this);
        }

    private void numOfPlayersChoice() throws IOException, ClassNotFoundException {
        sendConfigurationMessage(new ServerAskForNumOfPlayer());
        NumOfPlayerChoiceMessage message = (NumOfPlayerChoiceMessage)inputStream.readObject();
        numOfPlayers = message.getNumOfPlayers();
    }

    private void gameChoice() throws IOException, ClassNotFoundException {
        sendConfigurationMessage(new ServerAskForGameMode(this.nickname));
        GameModeChoiceMessage message = (GameModeChoiceMessage) inputStream.readObject();
        String choice = message.getGameModeChoice();
        if(choice.equalsIgnoreCase("multiplayer")) mode = GameMode.MULTIPLAYER;
        else {
            if(choice.equalsIgnoreCase("solo")) mode = GameMode.SOLO;
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


