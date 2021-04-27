package it.polimi.ingsw.Network;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Utils.GameMode;

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
    DataOutputStream outputStream;
    DataInputStream inputStream;



    public ClientSetupConnection(Server server, Socket clientSocket){
        this.server = server;
        this.clientSocket = clientSocket;
    }


    /*Method to initialize numOfPlayer and nickName*/
    public void run() {
        try {
            inputStream = new DataInputStream(clientSocket.getInputStream());
            outputStream = new DataOutputStream(clientSocket.getOutputStream());
            outputStream.writeUTF("Choose your nickname: ");
            outputStream.flush();
            nicknameChoice();
            if (server.inactivePlayerAlreadyRegistered(nickname)) {
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
        } catch (IOException e) {
            LOGGER.log(Level.INFO, "Reset connection");
        }
    }


    private void nicknameChoice() throws IOException {
        String nickname;
        boolean availableNickname = false;
        do {
            nickname = inputStream.readUTF();
            if (server.playerWithSameNicknameIsPlaying(nickname)) {
                outputStream.writeUTF("Nickname not available, choose another one: ");
                LOGGER.log(Level.INFO, "Player has chosen an unavailable nickname, connection refused");
            } else availableNickname = true;
        } while (!availableNickname);
        this.nickname = nickname;
    }

    private void numOfPlayersChoice() throws IOException {
        do {
            outputStream.writeUTF("Choose the number of players [2-4] : ");
            outputStream.flush();
            numOfPlayers = inputStream.read();
        } while (numOfPlayers < 1 || numOfPlayers > 4);
    }

    private void gameChoice() throws IOException {
        //TODO da cambiare con CLI/GUI
        outputStream.writeUTF("Choose game mode: ");
        String choice = inputStream.readUTF();
        if(choice.equalsIgnoreCase("multiplayer")) mode = GameMode.MULTIPLAYER;
        else {
            if(choice.equalsIgnoreCase("solo")) mode = GameMode.SOLO;
            outputStream.writeUTF("The game will start soon...");
            outputStream.flush();
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


}


