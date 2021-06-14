package it.polimi.ingsw.Model;

import it.polimi.ingsw.Client.reducedmodel.ReducedMarketTray;
import it.polimi.ingsw.Client.reducedmodel.ReducedMultiPlayerMode;
import it.polimi.ingsw.Client.reducedmodel.ReducedPlayer;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Model.ConclusionEvents.HitLastSpace;
import it.polimi.ingsw.Model.ConclusionEvents.SeventhDevCardBought;
import it.polimi.ingsw.Utils.Messages.ServerMessages.GameSetupMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.LastTurnMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.RankMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.FaithMarkerUpdate;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.NewTurnUpdate;
import it.polimi.ingsw.Utils.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class MultiPlayerMode extends Game {
    private final int numOfPlayers;
    private boolean isLastTurn = false;

    public MultiPlayerMode(Player inkwell, List<Player> playerList, Player currPlayer, int numOfPlayers) {
        this.inkwell = inkwell;
        this.playerList = playerList;
        this.currPlayer = currPlayer;
        this.numOfPlayers = numOfPlayers;
        this.gameState = GameState.SETUP;
    }

    public MultiPlayerMode(List<Player> playerList) {
        Collections.shuffle(playerList);
        this.playerList = playerList;
        this.inkwell = playerList.get(0);
        this.numOfPlayers = playerList.size();
        this.currPlayer = inkwell;
        gameSetup();
    }
    private void gameSetup()
    {
        this.setup();
        this.nextState(GameState.LEADERCHOICE);
    }

    public synchronized void nextTurn(){
        if(isLastTurn && currPlayer.getPosition()==numOfPlayers) {
            concludeGame();
        } else {
            this.currPlayer = nextPlayer(this.currPlayer);
            this.turn = new Turn(turn.getGame(), currPlayer);
            notifyTurn(new NewTurnUpdate(currPlayer.getUser()));
        }
    }




    public Player nextPlayer(Player currPlayer)
    {
        Player nextPlayer = playerList.get((playerList.indexOf(currPlayer)+1) % playerList.size());
        if(nextPlayer.getUser().isActive())
            return nextPlayer;
        else return nextPlayer(nextPlayer);
    }

    @Override
    public void notifyGameSetup() {
        notify(new GameSetupMessage(this.getReducedVersion()));
    }

    public ReducedMultiPlayerMode getReducedVersion() {
        List<ReducedPlayer> players = playerList.stream().map(Player::getReducedVersion).collect(Collectors.toList());
        ReducedMarketTray marketTray = this.marketTray.getReducedVersion();
        return new ReducedMultiPlayerMode(players, decks, marketTray);
    }

    @Override
    public void endGame(HitLastSpace event){
        isLastTurn = true;
        notify(new LastTurnMessage(this.currPlayer.getUser(),event));
    }

    @Override
    public void endGame(SeventhDevCardBought event){
        isLastTurn = true;
        notify(new LastTurnMessage(this.currPlayer.getUser(),event));
    }

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

    // returns rank only for test purposes
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

    @Override
    public void handlePlayerDisconnection(Player disconnectedPlayer) {
        if(playerList.stream().anyMatch(x -> x.getUser().isActive()))
            nextTurn();
        else {
            //TODO sospendere il gioco.
        }
    }
}

