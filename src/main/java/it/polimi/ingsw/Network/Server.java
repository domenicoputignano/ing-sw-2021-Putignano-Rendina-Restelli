package it.polimi.ingsw.Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private final int PORT;
    private final ServerSocket serverSocket;
    private final Logger LOGGER = Logger.getLogger(Server.class.getName());

    //Players involved in one match (connected or not)
    private final Map<String, ClientSetupConnection> accounts = new HashMap<>();

    private boolean active = false;
    private ExecutorService executors = Executors.newCachedThreadPool();


    public Server(int PORT) throws IOException {
        this.PORT = PORT;
        this.serverSocket = new ServerSocket(PORT);
    }

    public void start() {
        LOGGER.log(Level.INFO, "Server is ready to accept connections...");
        active = true;
        while(active) {
            try {
                Socket socket = serverSocket.accept();
                LOGGER.log(Level.INFO, "Connection established");
                ClientSetupConnection clientSetupConnection = new ClientSetupConnection(this, socket);
                executors.submit(clientSetupConnection);
            } catch (IOException e) {
                LOGGER.log(Level.INFO, "Unable to connect to client");
            }

        }
    }


    public void lobby(ClientSetupConnection clientSetupConnection) {

    }

    //method to detect if a player with the same nickname is already playing
    public boolean playerWithSameNicknameIsPlaying(String nickname) {
        return accounts.containsKey(nickname) && accounts.get(nickname).isActive();
    }

    public boolean inactivePlayerAlreadyRegistered(String nickname) {
        return accounts.containsKey(nickname) && !accounts.get(nickname).isActive();
    }

    public void registerClient() {

    }

}
