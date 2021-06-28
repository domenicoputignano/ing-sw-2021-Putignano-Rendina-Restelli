package it.polimi.ingsw.network;

import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.MultiPlayerMode;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.soloMode.SoloMode;
import it.polimi.ingsw.utils.messages.serverMessages.JoinLobbyMessage;

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
    private final Set<ClientStatus> waitingConnections = new HashSet<>();
    private final ExecutorService pingSenders = Executors.newCachedThreadPool();

    /**
     * Username of players involved in a match (connected or not)
     */
    private final Map<String, ClientStatus> accounts = new HashMap<>();

    private boolean active = false;

    /**
     * Method used to send pong messages back to clients.
     * @param pingSend function that sends pong message.
     */
    public void reply(Runnable pingSend) {
        pingSenders.submit(pingSend);
    }


    /**
     * Initializes a server reachable from remote hosts.
     * @param PORT endpoint over which the server is listening for connections.
     */
    public Server(int PORT) throws IOException {
        this.PORT = PORT;
        this.serverSocket = new ServerSocket(PORT);
    }

    /**
     * Method that runs infinitely until the server is shutdown.
     * It accepts inbound connections, sets a timeout for them and creates a new thread that will handle each one of them.
     */
    public void start() {
        LOGGER.log(Level.INFO, "Server is ready to accept connections...");
        active = true;
        while(active) {
            try {
                Socket socket = serverSocket.accept();
                socket.setSoTimeout(5000);
                new Thread(new ClientStatus(socket,this)).start();
                LOGGER.log(Level.INFO, "Connection established");
            } catch (IOException e) {
                LOGGER.log(Level.INFO, "Unable to connect to client");
            }
        }
    }

    /**
     * Adds a remote host to the set of client that are waiting for a game to start.
     * @param awaitingConnection client that waits for a game to start after nickname choice step.
     */
    public synchronized void addWaitingPlayer(ClientStatus awaitingConnection) {
        waitingConnections.add(awaitingConnection);
    }


    /**
     * The method handles decisions about wait or start a match, based on the number of players that are waiting.
     * This works as follows: basically the first guest selects number of players for a multiplayer mode and then
     * waits until number is reached, looking for clients that wants to play same kind of game.
     * When required number is reached, the match starts and Client Status are removed from those who are waiting.
     * Server supports multiple matches, so this process is repeated each time a client connects.
     * @param clientStatus represents remote host that has just connected and selected a multiplayer mode.
     */
    public synchronized void lobby(ClientStatus clientStatus) {
        Set<ClientStatus> suitableConnections = getSuitableConnections(clientStatus.getNumOfPlayers());
        if(suitableConnections.size() == clientStatus.getNumOfPlayers()) {
            initializeGame(suitableConnections);
            waitingConnections.removeAll(suitableConnections);
        }
        LOGGER.log(Level.INFO, "Waiting connections "+ waitingConnections);
    }

    /**
     * Boolean method to detect if a player is already playing a game when it tries to reconnect.
     * @param nickname nickname chosen by remote host.
     */
    public synchronized boolean isPlayerWithSameNicknamePlaying(String nickname) {
        if(accounts.containsKey(nickname)&&accounts.get(nickname).isActive()) {
            LOGGER.log(Level.INFO, "Player "+nickname+" is currently playing");
        }
        return accounts.containsKey(nickname) && accounts.get(nickname).isActive();
    }


    /**
     * Boolean method to detect if a player was playing a game and has disconnected from it.
     * @param nickname nickname chosen by remote host.
     */
    public boolean inactivePlayerAlreadyRegistered(String nickname) {
        return accounts.containsKey(nickname) && !accounts.get(nickname).isActive();
    }

    /**
     * Returns the number of players that are needed to start a game.
     * @param numOfPlayersChosen number of players that will play a game.
     */
    public int getNumOfMissingPlayers(int numOfPlayersChosen) {
        return numOfPlayersChosen - getSuitableConnections(numOfPlayersChosen).size();
    }

    /**
     * Returns a list of nickname that are waiting for a game to start.
     * @param numOfPlayersChosen number of players required to start a game.
     */
    private List<String> getAwaitingGuests(int numOfPlayersChosen) {
        return getSuitableConnections(numOfPlayersChosen).stream().map(ClientStatus::getNickname).collect(Collectors.toList());
    }

    /**
     * According to number of players chosen, it notifies remote hosts that a guest has just joined the lobby.
     * In this way clients are able to know how many players miss to start the game.
     */
    public synchronized void notifyLobbyJoin(int numOfPlayersChosen, String guest) {
        for(ClientStatus awaitingConnection : getSuitableConnections(numOfPlayersChosen)) {
            awaitingConnection.send(new JoinLobbyMessage(guest,getAwaitingGuests(numOfPlayersChosen), getNumOfMissingPlayers(numOfPlayersChosen)));
        }
    }

    /**
     * Returns a set of {@link ClientStatus} that have chosen the same number of players for a game.
     */
    private Set<ClientStatus> getSuitableConnections(int numOfPlayers) {
        return waitingConnections.stream().filter(x -> x.getNumOfPlayers()==numOfPlayers).collect(Collectors.toSet());
    }

    private Player getClientAsPlayer(ClientStatus client) {
        return new Player(client.getNickname());
    }

    /**
     * Initializes a multiplayer mode game and its controller.
     * @param clients remote hosts involved in new game.
     */
    public synchronized void initializeGame(Set<ClientStatus> clients) {
        List<Player> players = new ArrayList<>();

        //registration of each player
        for(ClientStatus client : clients) {
            players.add(getClientAsPlayer(client));
        }
        //Creation of model
        MultiPlayerMode multiPlayerMode = new MultiPlayerMode(players);
        GameController gameController = new GameController(multiPlayerMode);
        for(Player player : players) {
            NetworkRemoteView remoteView = new NetworkRemoteView(player.getUser(), gameController, accounts.get(player.getUser().getNickname()));
            accounts.get(player.getUser().getNickname()).bindRemoteView(remoteView);
        }
        multiPlayerMode.notifyGameSetup();
    }


    /**
     * Initializes a solo mode game.
     * @param client remote host that plays in solo mode.
     */
    public synchronized void initializeGame(ClientStatus client) {
        waitingConnections.remove(client);
        Player player = getClientAsPlayer(client);
        SoloMode soloMode = new SoloMode(player);
        GameController gameController = new GameController(soloMode);
        NetworkRemoteView remoteView = new NetworkRemoteView(player.getUser(), gameController, client);
        client.bindRemoteView(remoteView);
        soloMode.notifyGameSetup();
        LOGGER.log(Level.INFO, "Solo mode game setup done");
    }

    /**
     * This method allows player reconnection. It basically creates a new {@link NetworkRemoteView} to a game instance already created
     * and binds it with the client that is reconnecting.
     * @param reconnectingClient remote host that wants to join after a disconnection.
     */
    public void resumeGame(ClientStatus reconnectingClient) {
        ClientStatus oldClientStatus = accounts.get(reconnectingClient.getNickname());
        NetworkRemoteView oldRemoteView = oldClientStatus.getRemoteView();
        //ClientStatus newClientStatus = new ClientStatus(client.getClientSocket(), client.getInputStream(), client.getOutputStream());
        NetworkRemoteView newRemoteView = new NetworkRemoteView(oldRemoteView, reconnectingClient);
        reconnectingClient.bindRemoteView(newRemoteView);
        //accounts.remove(client.getNickname());
        accounts.put(reconnectingClient.getNickname(), reconnectingClient);
        newRemoteView.handlePlayerReconnection(new User(reconnectingClient.getNickname()));
    }


    /**
     * It removes a remote host if it disconnects without completing configuration.
     * Nickname chosen is deleted and the {@link ClientStatus} won't be available for any game.
     * @param clientNotSetup client that lost or closed the connection.
     */
    public synchronized void removeNotSetupPlayer(ClientStatus clientNotSetup) {
        waitingConnections.remove(clientNotSetup);
        accounts.remove(clientNotSetup.getNickname());
    }

    /**
     * Adds a {@link ClientStatus} and its nickname to registered accounts.
     * @param nickname unique nickname chosen by remote host.
     */
    public synchronized void registerClientStatus(String nickname, ClientStatus clientStatus) {
        accounts.put(nickname,clientStatus);
    }


    /**
     * It deletes a game after all players have disconnected.
     * @param usersInAPausedMessage users involved a paused match.
     */
    public synchronized void deleteMatch(List<User> usersInAPausedMessage) {
        usersInAPausedMessage.forEach((x) -> {
            accounts.get(x.getNickname()).closeConnection();
            accounts.remove(x.getNickname());
        });
    }

}
