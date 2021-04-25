package it.polimi.ingsw.Network;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientSetupConnection implements Runnable {
    private final Logger LOGGER = Logger.getLogger(ClientSetupConnection.class.getName());
    private final Socket clientSocket;
    private final Server server;
    private String nickname;
    private int numOfPlayers;
    private boolean isActive = false;
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
                numOfPlayersChoice();
                isActive = true;
                LOGGER.log(Level.INFO, "Client " + nickname + " connected and " + numOfPlayers + " players chosen");
            }
        } catch (IOException e) {
            LOGGER.log(Level.INFO, "Reset connection");
        }
    }



    public boolean isActive(){
        return isActive;
    }

    private void nicknameChoice() throws IOException {
        String nickname;
        boolean availableNickname = false;
        do {
            nickname = inputStream.readUTF();
            if (server.playerWithSameNicknameIsPlaying(nickname)) {
                outputStream.writeUTF("Nickname not available, choose another one!");
                LOGGER.log(Level.INFO, "Player has chosen an unavailable nickname, connection refused");
            } else availableNickname = true;
        } while (!availableNickname);
        this.nickname = nickname;
    }

    private void numOfPlayersChoice() throws IOException {
        do {
            outputStream.writeUTF("Choose the number of players [2-4] : ");
            numOfPlayers = inputStream.read();
        } while (numOfPlayers < 1 || numOfPlayers > 4);
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


