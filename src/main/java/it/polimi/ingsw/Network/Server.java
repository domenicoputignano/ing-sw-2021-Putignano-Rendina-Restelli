package it.polimi.ingsw.Network;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.MultiPlayerMode;
import it.polimi.ingsw.Model.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Server {
    private final int PORT;
    private final ServerSocket serverSocket;
    private final Logger LOGGER = Logger.getLogger(Server.class.getName());
    private final Set<ClientSetupConnection> waitingConnections = new HashSet<>();

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
        waitingConnections.add(clientSetupConnection);
        Set<ClientSetupConnection> suitableConnections = getSuitableConnections(clientSetupConnection.getNumOfPlayers());
        if(suitableConnections.size() == clientSetupConnection.getNumOfPlayers()) {

        }
    }

    //method to detect if a player with the same nickname is already playing
    public boolean playerWithSameNicknameIsPlaying(String nickname) {
        return accounts.containsKey(nickname) && accounts.get(nickname).isActive();
    }

    public boolean inactivePlayerAlreadyRegistered(String nickname) {
        return accounts.containsKey(nickname) && !accounts.get(nickname).isActive();
    }

    private Set<ClientSetupConnection> getSuitableConnections(int numOfPlayers) {
        return waitingConnections.stream().filter(x -> x.getNumOfPlayers()==numOfPlayers).collect(Collectors.toSet());
    }


    private List<Player> initializePlayers(Set<ClientSetupConnection> clients) {
        return clients.stream().map(x -> new Player(x.getNickname())).collect(Collectors.toList());
    }

    private Player getClientAsPlayer(ClientSetupConnection client) {
        return new Player(client.getNickname());
    }

    private void initializeGame(Set<ClientSetupConnection> clients) {
        GameController gameController = new GameController(initializePlayers(clients));
        for(ClientSetupConnection client : clients) {
            RemoteView remoteView = new RemoteView(getClientAsPlayer(client), gameController);
            ClientStatus clientStatus = new ClientStatus(remoteView, client.getClientSocket());
        }
    }

}
