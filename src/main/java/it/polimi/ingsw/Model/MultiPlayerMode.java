package it.polimi.ingsw.Model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.Model.Card.CardType;
import it.polimi.ingsw.Model.Card.Deck;
import it.polimi.ingsw.Model.Card.LeaderCard;
import it.polimi.ingsw.Model.MarketTray.MarketTray;
import it.polimi.ingsw.Observer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.*;

public class MultiPlayerMode extends Game {
    private int numOfPlayers;

    public MultiPlayerMode(Player inkwell, List<Player> playerList, Player currPlayer, int numOfPlayers) {
        this.inkwell = inkwell;
        this.playerList = playerList;
        this.currPlayer = currPlayer;
        this.numOfPlayers = numOfPlayers;
        this.gameState = GameState.SETUP;
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




}
