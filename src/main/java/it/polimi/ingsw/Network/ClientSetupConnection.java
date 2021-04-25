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

    public void run() {
        try {
            inputStream = new DataInputStream(clientSocket.getInputStream());
            outputStream = new DataOutputStream(clientSocket.getOutputStream());
            outputStream.writeUTF("Choose your nickname: ");
            outputStream.flush();
            String nickname = inputStream.readUTF();

            if(server.playerWithSameNicknameIsPlaying(nickname)) {
                outputStream.writeUTF("Nickname not available, choose another one!");
                LOGGER.log(Level.INFO, "Player has chosen an unavailable nickname, registration rejected");
                nickname = inputStream.readUTF();
            }
            outputStream.writeUTF("Choose the number of players [2-4] : ");
            numOfPlayers = inputStream.read();
            isActive = true;
            LOGGER.log(Level.INFO, "Client " + nickname + " connected and " + numOfPlayers + " players chosen");
        } catch(IOException e) {
            LOGGER.log(Level.INFO, "Reset connection");
        }

    }


    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isActive() {
        return isActive;
    }

}

