package it.polimi.ingsw.Model;

import it.polimi.ingsw.Client.reducedmodel.ReducedMarketTray;
import it.polimi.ingsw.Client.reducedmodel.ReducedMultiPlayerMode;
import it.polimi.ingsw.Client.reducedmodel.ReducedPlayer;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Model.ConclusionEvents.BlackCrossHitLastSpace;
import it.polimi.ingsw.Model.ConclusionEvents.DevCardColorEnded;
import it.polimi.ingsw.Model.ConclusionEvents.HitLastSpace;
import it.polimi.ingsw.Model.ConclusionEvents.SeventhDevCardBought;
import it.polimi.ingsw.Utils.Messages.ServerMessages.GameSetupMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.LastTurnMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.RankMessage;

import java.util.*;
import java.util.stream.Collectors;

public class MultiPlayerMode extends Game {
    private int numOfPlayers;
    private int receivedInitialMessages;
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
    /*
    public Player getWinner() {
        //TODO
        return null;
    }

    public int calcVictoryPoints() {
        //TODO
        return 1;

    }*/

    private void gameSetup()
    {
        this.setup();
        this.nextState(GameState.LEADERCHOICE);
    }

    public void nextTurn(){
        if(isLastTurn && currPlayer.getPosition()==numOfPlayers) {
            concludeGame();
        }
        else{
            this.currPlayer = nextPlayer(this.currPlayer);
            this.turn = new Turn(turn.getGame(), currPlayer);
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

    public void concludeGame()
    {
        Map<User,Integer> rank = new HashMap<>();
        Map<User,Integer> sortedRank = new HashMap<>();
        for(Player p: playerList)
        {
            rank.put(p.getUser(),p.calcVictoryPointsPlayer());
        }
        rank.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).forEach(x -> sortedRank.put(x.getKey(),x.getValue()));
        notify(new RankMessage(sortedRank));
    }
}

