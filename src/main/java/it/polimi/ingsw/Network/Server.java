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
    private final ExecutorService setupManagers = Executors.newCachedThreadPool();

    //username of players involved in one match (connected or not)
    private final Map<String, ClientStatus> accounts = new HashMap<>();

    private boolean active = false;


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
                setupManagers.submit(clientSetupConnection);
            } catch (IOException e) {
                LOGGER.log(Level.INFO, "Unable to connect to client");
                active = false;
            }

        }
    }

    public void addWaitingPlayer(ClientSetupConnection awaitingConnection) {
        waitingConnections.add(awaitingConnection);
    }

    public void lobby(ClientSetupConnection clientSetupConnection) {
        Set<ClientSetupConnection> suitableConnections = getSuitableConnections(clientSetupConnection.getNumOfPlayers());
        if(suitableConnections.size() == clientSetupConnection.getNumOfPlayers()) {
            initializeGame(suitableConnections);
            waitingConnections.removeAll(suitableConnections);
        }
        LOGGER.log(Level.INFO, "Waiting connections "+ waitingConnections);
    }

    //method to detect if a player with the same nickname is already playing
    public boolean isPlayerWithSameNicknamePlaying(String nickname) {
        if(accounts.containsKey(nickname)&&accounts.get(nickname).isActive()) {
            LOGGER.log(Level.INFO, "Player "+nickname+" is currently playing");
        }

        return accounts.containsKey(nickname) && accounts.get(nickname).isActive();
    }

    public boolean inactivePlayerAlreadyRegistered(String nickname) {
        return accounts.containsKey(nickname) && !accounts.get(nickname).isActive();
    }

    /*public boolean isNicknameAvailableBeforeStarting(String nickname) {
        return waitingConnections.stream().anyMatch(x -> x.getNickname().equals(nickname));
    }*/

    public int getNumOfMissingPlayers(int numOfPlayersChosen) {
        return numOfPlayersChosen - getSuitableConnections(numOfPlayersChosen).size();
    }

    private List<String> getAwaitingGuests(int numOfPlayersChosen) {
        return getSuitableConnections(numOfPlayersChosen).stream().map(ClientSetupConnection::getNickname).collect(Collectors.toList());
    }

    public void notifyLobbyJoin(int numOfPlayersChosen, String guest) throws IOException {
        for(ClientSetupConnection awaitingConnection : getSuitableConnections(numOfPlayersChosen)) {
            awaitingConnection.sendConfigurationMessage(new JoinLobbyMessage(guest,getAwaitingGuests(numOfPlayersChosen), getNumOfMissingPlayers(numOfPlayersChosen)));
        }
    }

    private Set<ClientSetupConnection> getSuitableConnections(int numOfPlayers) {
        return waitingConnections.stream().filter(x -> x.getNumOfPlayers()==numOfPlayers).collect(Collectors.toSet());
    }

    private Player getClientAsPlayer(ClientSetupConnection client) {
        return new Player(client.getNickname());
    }

    public void initializeGame(Set<ClientSetupConnection> clients) {
        List<Player> players = new ArrayList<>();
        for(ClientSetupConnection clientSetupConnection : clients) {
            ClientStatus clientStatus = new ClientStatus(clientSetupConnection.getClientSocket(),
                    clientSetupConnection.getInputStream(),
                    clientSetupConnection.getOutputStream());

            new Thread(clientStatus).start();

            //registration of each player
            accounts.put(clientSetupConnection.getNickname(), clientStatus);

            players.add(getClientAsPlayer(clientSetupConnection));
            //created players and players-connections binding
        }
        //Creation of model
        MultiPlayerMode multiPlayerMode = new MultiPlayerMode(players);
        GameController gameController = new GameController(multiPlayerMode);
        for(Player p : players) {
            RemoteView remoteView = new RemoteView(p.getUser(), gameController, accounts.get(p.getUser().getNickname()));
            accounts.get(p.getUser().getNickname()).bindRemoteView(remoteView);
        }
        //todo AGGIUNTO da Piero
        multiPlayerMode.notifyGameSetup();
        LOGGER.log(Level.INFO, "MultiPlayerMode game setup done");

    }


    public void initializeGame(ClientSetupConnection client) {
        waitingConnections.remove(client);
        Player player = getClientAsPlayer(client);
        ClientStatus clientStatus = new ClientStatus(client.getClientSocket(),client.getInputStream(), client.getOutputStream());
        new Thread(clientStatus).start();

        // registration of the player
        accounts.put(client.getNickname(), clientStatus);
        LOGGER.log(Level.INFO, "New account registered with nickname " + client.getNickname());

        SoloMode soloMode = new SoloMode(player);
        GameController gameController = new GameController(soloMode);
        RemoteView remoteView = new RemoteView(player.getUser(), gameController, clientStatus);
        accounts.get(player.getUser().getNickname()).bindRemoteView(remoteView);
        LOGGER.log(Level.INFO, "Starting a new SoloMode game...");
        soloMode.notifyGameSetup();
        LOGGER.log(Level.INFO, "SoloMode game setup done");
    }

    public void resumeGame(ClientSetupConnection client) {
        ClientStatus oldClientStatus = accounts.get(client.getNickname());
        RemoteView oldRemoteView = oldClientStatus.getRemoteView();
        ClientStatus newClientStatus = new ClientStatus(client.getClientSocket(), client.getInputStream(), client.getOutputStream());
        RemoteView newRemoteView = new RemoteView(oldRemoteView, newClientStatus);
        newClientStatus.bindRemoteView(newRemoteView);
        accounts.remove(client.getNickname());
        accounts.put(client.getNickname(), newClientStatus);
        new Thread(newClientStatus).start();
        newRemoteView.handlePlayerReconnection(new User(client.getNickname()));
        //newRemoteView.getModel().handlePlayerReconnection(new User(client.getNickname()));
        /*
        newRemoteView.getModel().notifyGameResumed(new User(client.getNickname()));
        */
    }

    //TODO da togliere, metodo fatto per creare togliere un'istanza di un giocatore che non ha completato la configurazione
    public void removeNotSetupPlayer(ClientSetupConnection clientNotSetup) {
        waitingConnections.remove(clientNotSetup);
    }

}
