package it.polimi.ingsw.Network;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.MultiPlayerMode;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.SoloMode.SoloMode;
import it.polimi.ingsw.Utils.GameMode;

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
    private final Map<String, ClientStatus> accounts = new HashMap<>();

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
            initializeGame(suitableConnections);
            waitingConnections.removeAll(suitableConnections);
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



    private Player getClientAsPlayer(ClientSetupConnection client) {
        return new Player(client.getNickname());
    }

    public void initializeGame(Set<ClientSetupConnection> clients) {
        List<Player> players = new ArrayList<>();
        for(ClientSetupConnection client : clients) {
            ClientStatus clientStatus = new ClientStatus(client.getClientSocket());
            executors.submit(clientStatus);

            //registration of each player
            accounts.put(client.getNickname(), clientStatus);

            players.add(getClientAsPlayer(client));
            //created players and players-connections binding
        }
        //Creation of model
        MultiPlayerMode multiPlayerMode = new MultiPlayerMode(players);
        GameController gameController = new GameController(multiPlayerMode);
        for(Player p : players) {
            RemoteView remoteView = new RemoteView(p, gameController, accounts.get(p.getUsername()));
            accounts.get(p.getUsername()).bindRemoteView(remoteView);
        }
    }


    public void initializeGame(ClientSetupConnection client) {
        Player player = getClientAsPlayer(client);
        ClientStatus clientStatus = new ClientStatus(client.getClientSocket());
        executors.submit(clientStatus);

        // registration of the player
        accounts.put(client.getNickname(), clientStatus);
        LOGGER.log(Level.INFO, "New account registered with nickname " + client.getNickname());

        SoloMode soloMode = new SoloMode(player);
        GameController gameController = new GameController(soloMode);
        RemoteView remoteView = new RemoteView(player, gameController, clientStatus);
        accounts.get(player.getUsername()).bindRemoteView(remoteView);

        LOGGER.log(Level.INFO, "Starting a new SoloMode game...");
    }

    public Map<String, ClientStatus> getAccounts() {
        return accounts;
    }
}
