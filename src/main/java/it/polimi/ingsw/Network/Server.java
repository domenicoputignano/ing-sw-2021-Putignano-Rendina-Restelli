package it.polimi.ingsw.Network;

import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Model.MultiPlayerMode;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.SoloMode.SoloMode;
import it.polimi.ingsw.Utils.Messages.ServerMessages.JoinLobbyMessage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
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

    //username of players involved in one match (connected or not)
    private final Map<String, ClientStatus> accounts = new HashMap<>();

    private boolean active = false;

    public void reply(Runnable pingSend) {
        pingSenders.submit(pingSend);
    }


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
                socket.setSoTimeout(5000);
                new Thread(new ClientStatus(socket,this)).start();
                LOGGER.log(Level.INFO, "Connection established");
            } catch (IOException e) {
                LOGGER.log(Level.INFO, "Unable to connect to client");
                active = false;
            }
        }
    }

    public synchronized void addWaitingPlayer(ClientStatus awaitingConnection) {
        waitingConnections.add(awaitingConnection);
    }

    public synchronized void lobby(ClientStatus clientStatus) {
        Set<ClientStatus> suitableConnections = getSuitableConnections(clientStatus.getNumOfPlayers());
        if(suitableConnections.size() == clientStatus.getNumOfPlayers()) {
            initializeGame(suitableConnections);
            waitingConnections.removeAll(suitableConnections);
        }
        LOGGER.log(Level.INFO, "Waiting connections "+ waitingConnections);
    }

    //method to detect if a player with the same nickname is already playing
    public synchronized boolean isPlayerWithSameNicknamePlaying(String nickname) {
        if(accounts.containsKey(nickname)&&accounts.get(nickname).isActive()) {
            LOGGER.log(Level.INFO, "Player "+nickname+" is currently playing");
        }

        return accounts.containsKey(nickname) && accounts.get(nickname).isActive();
    }

    public boolean inactivePlayerAlreadyRegistered(String nickname) {
        return accounts.containsKey(nickname) && !accounts.get(nickname).isActive();
    }


    public int getNumOfMissingPlayers(int numOfPlayersChosen) {
        return numOfPlayersChosen - getSuitableConnections(numOfPlayersChosen).size();
    }

    private List<String> getAwaitingGuests(int numOfPlayersChosen) {
        return getSuitableConnections(numOfPlayersChosen).stream().map(ClientStatus::getNickname).collect(Collectors.toList());
    }

    public synchronized void notifyLobbyJoin(int numOfPlayersChosen, String guest) {
        for(ClientStatus awaitingConnection : getSuitableConnections(numOfPlayersChosen)) {
            awaitingConnection.send(new JoinLobbyMessage(guest,getAwaitingGuests(numOfPlayersChosen), getNumOfMissingPlayers(numOfPlayersChosen)));
        }
    }

    private Set<ClientStatus> getSuitableConnections(int numOfPlayers) {
        return waitingConnections.stream().filter(x -> x.getNumOfPlayers()==numOfPlayers).collect(Collectors.toSet());
    }

    private Player getClientAsPlayer(ClientStatus client) {
        return new Player(client.getNickname());
    }

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


    public synchronized void removeNotSetupPlayer(ClientStatus clientNotSetup) {
        waitingConnections.remove(clientNotSetup);
        accounts.remove(clientNotSetup.getNickname());
    }

    public synchronized void registerClientStatus(String nickname, ClientStatus clientStatus) {
        accounts.put(nickname,clientStatus);
    }

}
