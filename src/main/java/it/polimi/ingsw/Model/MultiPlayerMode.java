package it.polimi.ingsw.Model;

import it.polimi.ingsw.Client.reducedmodel.ReducedMarketTray;
import it.polimi.ingsw.Client.reducedmodel.ReducedMultiPlayerMode;
import it.polimi.ingsw.Client.reducedmodel.ReducedPlayer;
import it.polimi.ingsw.Commons.StateFavorTiles;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Model.ConclusionEvents.HitLastSpace;
import it.polimi.ingsw.Model.ConclusionEvents.SeventhDevCardBought;
import it.polimi.ingsw.Utils.Messages.ServerMessages.GameSetupMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.LastTurnMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.RankMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.ActivateVaticanReportUpdate;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.FaithMarkerUpdate;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.NewTurnUpdate;
import it.polimi.ingsw.Utils.Messages.ServerMessages.UserDisconnectedMessage;
import it.polimi.ingsw.Utils.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * This class extends the main class of the model {@link Game}, implementing one of the two possible
 * modalities of Maestri del Rinascimento board game. This mode can be played by 2 to 4 players and
 * is won by the player who gains more victory points.
 */
public class MultiPlayerMode extends Game {
    /**
     * The utility logger.
     */
    Logger LOGGER = Logger.getLogger(this.getClass().getName());
    /**
     * The number of players in game.
     */
    private final int numOfPlayers;
    /**
     * Flag to know if the last turn has been reached.
     */
    private boolean isLastTurn = false;

    /**
     * Custom constructor used for test purposes only.
     */
    public MultiPlayerMode(Player inkwell, List<Player> playerList, Player currPlayer, int numOfPlayers) {
        this.inkwell = inkwell;
        this.playerList = playerList;
        this.currPlayer = currPlayer;
        this.numOfPlayers = numOfPlayers;
        this.gameState = GameState.SETUP;
    }

    /**
     * Constructs a multiplayer mode game instance from the given list of players.
     * Shuffles them in order to create a random turn logic order, then begins the setup phase of the game.
     * @param playerList the players who will play this game.
     */
    public MultiPlayerMode(List<Player> playerList) {
        Collections.shuffle(playerList);
        this.playerList = playerList;
        this.inkwell = playerList.get(0);
        this.numOfPlayers = playerList.size();
        this.currPlayer = inkwell;
        gameSetup();
    }

    /**
     * Setups the game and then proceeds to the initial leader choice phase.
     */
    private void gameSetup() {
        this.setup();
        this.nextState(GameState.INITIALCHOICES);
    }

    /**
     * Proceeds to the next turn and notifies each remote view of the beginning of a new turn.
     * If this is the last turn and all the players in game have already played their turn
     * the game is concluded.
     */
    public synchronized void nextTurn(){
        if(isLastTurn && currPlayer.getPosition()==numOfPlayers) {
            concludeGame();
        } else {
            this.currPlayer = nextPlayer(this.currPlayer);
            this.turn = new Turn(turn.getGame(), currPlayer);
            notifyTurn(new NewTurnUpdate(currPlayer.getUser()));
        }
    }

    /**
     * Changes the current player by proceeding to the next player in positions order.
     * @param currPlayer the player that has just played his turn.
     * @return the new player in turn.
     */
    public Player nextPlayer(Player currPlayer) {
        Player nextPlayer = playerList.get((playerList.indexOf(currPlayer)+1) % playerList.size());
        if(nextPlayer.getUser().isActive())
            return nextPlayer;
        else return nextPlayer(nextPlayer);
    }

    /**
     * Notifies each remote view of the players in game when the game has been setup by sending them
     * the first simplified instance of the game.
     */
    @Override
    public void notifyGameSetup() {
        notify(new GameSetupMessage(this.getReducedVersion()));
    }

    /**
     * Retrieves a simplified instance of this class that can be sent through the network in order to update
     * the client's reduced model.
     * @return the reduced instance of this class.
     */
    public ReducedMultiPlayerMode getReducedVersion() {
        List<ReducedPlayer> players = playerList.stream().map(Player::getReducedVersion).collect(Collectors.toList());
        ReducedMarketTray marketTray = this.marketTray.getReducedVersion();
        return new ReducedMultiPlayerMode(players, decks, marketTray, this.currPlayer.getReducedVersion());
    }

    /**
     * Implementation of the method called when {@link HitLastSpace} conclusion event is reached.
     * Last turn must be played and all the players are notified.
     * @param event the event triggered.
     */
    @Override
    public void endGame(HitLastSpace event){
        isLastTurn = true;
        notify(new LastTurnMessage(this.currPlayer.getUser(),event));
    }

    /**
     * Implementation of the method called when {@link SeventhDevCardBought} conclusion event is reached.
     * Last turn must be played and all the players are notified.
     * @param event the event triggered.
     */
    @Override
    public void endGame(SeventhDevCardBought event){
        isLastTurn = true;
        notify(new LastTurnMessage(this.currPlayer.getUser(),event));
    }

    /**
     * Moves all the players except for the player who discarded the resources.
     * @param triggeringPlayer the player who discarded the resources and doesn't have to be moved.
     * @param discardedResources the number of spaces to move the players by.
     */
    @Override
    public void moveOtherPlayers(Player triggeringPlayer, int discardedResources) {
        for(Player p : playerList) {
            if(!p.equals(triggeringPlayer)) {
                p.getPersonalBoard().moveMarker(p,discardedResources);
            }
            turn.getGame().notifyUpdate(new FaithMarkerUpdate(p.getUser(),
                    p.getReducedPersonalBoard(),
                    triggeringPlayer.getUser(),
                    discardedResources));
        }
    }

    /**
     * Concludes the game, computes the final rank and notifies each remote view with the final rank computed.
     * @return rank computed for test purposes only.
     */
    public List<Pair<User, Integer>> concludeGame(){
        List<Pair<User, Integer>> rank = new ArrayList<>();
        for(Player p: playerList) {
            rank.add(new Pair<>(p.getUser(),p.calcVictoryPointsPlayer()));
        }
        rank = rank.stream().sorted(Collections.reverseOrder(Comparator.comparing(Pair::getValue))).
                collect(Collectors.toList());
        notify(new RankMessage(rank));
        return rank;
    }

    /**
     * Method called when a vatican report is activated by the triggering player on the given section.
     * Every players check their faith track: if their Faith Marker is on a space within or beyond the activated
     * Vatican Report section, turn the Pope's Favor tile of that section face-up, else discard the Pope's
     * Favor tile from that section.
     * Then notifies each remote view of the overcome of this Vatican Report activation.
     * @param triggeringPlayer the player who triggered the vatican report.
     * @param vatican_index the section index which the vatican report has been triggered on.
     */
    @Override
    public void activateVaticanReport(Player triggeringPlayer,int vatican_index) {
        int start = currPlayer.getPersonalBoard().getFaithTrack().getSections()[vatican_index].getStartSpace();
        for(Player p: playerList)
        {
            int position = p.getPersonalBoard().getFaithTrack().getFaithMarker();
            if(position >= start)
                p.getPersonalBoard().getFaithTrack().setFavorTile(vatican_index, StateFavorTiles.FACEUP);
            else p.getPersonalBoard().getFaithTrack().setFavorTile(vatican_index,StateFavorTiles.DISCARDED);
            notifyUpdate(new ActivateVaticanReportUpdate(p.getUser(),
                    p.getReducedPersonalBoard(), triggeringPlayer.getUser(),
                    p.getPersonalBoard().getFaithTrack().getStateFavorTile(vatican_index), vatican_index));
        }
    }

    /**
     * Handles the disconnection of a player.
     * If the player who disconnected was the player in turn, the turn is automatically passed to the next player.
     * If all players disconnected, the game is paused.
     * If the player disconnected during initial choices and he had the inkwell, a new first player
     * is computed.
     * @param disconnectedPlayer the player who disconnected.
     */
    @Override
    public synchronized void handlePlayerDisconnection(Player disconnectedPlayer) {
        if(this.gameState == GameState.GAMEFLOW) {
            if(playerList.stream().anyMatch(x -> x.getUser().isActive())) {
                notify(new UserDisconnectedMessage(disconnectedPlayer.getUser()));
                if(disconnectedPlayer.equals(currPlayer)) {
                    nextTurn();
                }
            }
            else {
                nextState(GameState.PAUSED);
            }
        }
        else if(this.gameState == GameState.INITIALCHOICES) {
                if(playerList.stream().anyMatch(x -> x.getUser().isActive())) {
                    this.currPlayer = nextPlayer(this.currPlayer);
                    this.turn = new Turn(turn.getGame(), currPlayer);
            }
            notify(new UserDisconnectedMessage(disconnectedPlayer.getUser()));
        }
    }

    /**
     * Handles the reconnection of a player.
     * If the game was paused and the player is the first to reconnect, the game is resumed and he starts
     * to play as the current player.
     * Otherwise all the other players are only notified of the reconnection.
     * @param reconnectingUser the user who reconnected.
     */
    @Override
    public synchronized void handlePlayerReconnection(User reconnectingUser) {
        if(gameState==GameState.PAUSED) {
            users.keySet().stream().filter( x-> x.equals(reconnectingUser)).findFirst().ifPresent(x -> x.setActive(true));
            LOGGER.log(Level.INFO, "First player reconnected");
            nextState(GameState.GAMEFLOW);
            currPlayer = getPlayer(reconnectingUser);
            turn = new Turn(turn.getGame(), currPlayer);
            notifyGameResumed(reconnectingUser);
            notifyTurn(new NewTurnUpdate(currPlayer.getUser()));
        } else {
            LOGGER.log(Level.INFO, "Player reconnected while others are playing");
            if(gameState == GameState.GAMEFLOW) {
                notifyGameResumed(reconnectingUser, currPlayer.getUser());
            }
        }
    }
}

