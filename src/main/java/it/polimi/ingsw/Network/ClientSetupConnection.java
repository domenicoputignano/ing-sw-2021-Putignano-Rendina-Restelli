package it.polimi.ingsw.Network;


import it.polimi.ingsw.Utils.GameMode;
import it.polimi.ingsw.Utils.Messages.ClientMessages.GameModeChoiceMessage;
import it.polimi.ingsw.Utils.Messages.ClientMessages.NumOfPlayerChoiceMessage;
import it.polimi.ingsw.Utils.Messages.ClientMessages.UsernameChoiceMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.NotAvailableNicknameMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerAskForGameMode;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerAskForNumOfPlayer;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerAsksForNickname;

import java.io.*;
import java.net.Socket;
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
                LOGGER.log(Level.INFO, "Player con lo stesso nickname già presente ma non attivo ");
                //TODO recuperare i dati della partita associata a quel giocatore
            }
            else {
                gameChoice();
                if(mode == GameMode.SOLO) {
                    server.initializeGame(this);
                }
                else {
                    numOfPlayersChoice();
                    LOGGER.log(Level.INFO, "Client " + nickname + " connected and " + numOfPlayers + " players chosen");
                    server.lobby(this);
                }

            }
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log(Level.INFO, "Reset connection");
        }
    }


    private void nicknameChoice() throws IOException, ClassNotFoundException {
        String nickname;
        boolean availableNickname = false;
        outputStream.writeObject(new ServerAsksForNickname());
        outputStream.flush();
        do {
            UsernameChoiceMessage messageFromClient = (UsernameChoiceMessage) inputStream.readObject();
            nickname = messageFromClient.getNickname();
            if(server.isNicknameAvailableBeforeStarting(nickname)) {
                outputStream.writeObject(new NotAvailableNicknameMessage());
                outputStream.flush();
            }
            else {
                if (server.isPlayerWithSameNicknamePlaying(nickname)) {
                //TODO cambiare con opportuno messaggio
                outputStream.writeObject(new NotAvailableNicknameMessage());
                outputStream.flush();
                LOGGER.log(Level.INFO, "Player has chosen an unavailable nickname, connection refused ");
                }
                else {
                    availableNickname = true;
                }
            }
        } while (!availableNickname);
        this.nickname = nickname;
        server.addWaitingPlayer(this);
    }

    private void numOfPlayersChoice() throws IOException, ClassNotFoundException {
        outputStream.writeObject(new ServerAskForNumOfPlayer());
        outputStream.flush();
        NumOfPlayerChoiceMessage message = (NumOfPlayerChoiceMessage)inputStream.readObject();
        numOfPlayers = message.getNumOfPlayers();
    }

    private void gameChoice() throws IOException, ClassNotFoundException {
        outputStream.writeObject(new ServerAskForGameMode());
        outputStream.flush();
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

}


