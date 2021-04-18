package it.polimi.ingsw.Model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.Model.Card.Deck;
import it.polimi.ingsw.Model.Card.LeaderCard;
import it.polimi.ingsw.Model.MarketTray.MarketTray;
import it.polimi.ingsw.Observer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.*;

public class Game implements Observer<Integer> {
    private Player inkwell;
    private List<Player> playerList;
    private Player currPlayer;
    private List<Deck> decks;
    private int numOfPlayers;
    private GameState gameState;
    private Turn turn;
    private MarketTray marketTray;



    public Game(Player inkwell, List<Player> playerList, Player currPlayer, int numOfPlayers) {
        this.inkwell = inkwell;
        this.playerList = playerList;
        this.currPlayer = currPlayer;
        this.numOfPlayers = numOfPlayers;
        this.gameState = GameState.SETUP;
    }

    public void setup()
    {
        this.marketTray = new MarketTray();
        this.initializeDecksDevCards();
        this.dealLeaderCards();
        for(Player p : this.playerList)
           {
               p.initializePersonalBoard();
               p.setPosition(playerList.indexOf(p)+1);

               /*aggiungo Game alla lista di Observer di faithtrack per la vatican report section*/
               p.getPersonalBoard().getFaithTrack().addObserver(this);

               if(p.getPosition()==3 || p.getPosition()==4)
                   p.getPersonalBoard().getFaithTrack().moveMarker(1);
           }
        this.turn = new Turn(this,inkwell);
    //DISTRIBUZIONE RISORSE A SCELTA E SCELTA CARTE LEADER PASSANDO IN RESOURCECHOICE E LEADER CHOICE
        //Inviare notifica alle view interessate per le risorse a scelta
    }

    public void nextState(GameState state)
    {
        this.gameState = state;
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

    public Player getCurrPlayer() {
        return currPlayer;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    private void initializeDecksDevCards() {
            String path = "src/main/resources/json/devCards.json";
            decks = new ArrayList<>();
            try{
                Gson gson = new Gson();

                JsonReader reader = new JsonReader(new FileReader(path));

                Type listType = new TypeToken<List<Deck>>() {
                }.getType();
                decks = gson.fromJson(reader, listType);
                shuffleDecksDevCards();
            } catch (FileNotFoundException e) {
                // mandare messaggio al client "Configuration file not found"
            }
    }


    public void dealLeaderCards() {
        List<LeaderCard> cards = initializeDeckLeaderCards();
        Random rand = new Random();
        for (Player p : playerList) {
            for (int i = 0; i < 4; i++) {
                int randNum = rand.nextInt(cards.size());
                LeaderCard card = cards.remove(randNum);
                p.getLeaderCards().add(card);
            }
        }
    }


    private void shuffleDecksDevCards() {
        for (Deck d : decks) {
            Collections.shuffle(d.getCards());
        }
    }

    private List<LeaderCard> initializeDeckLeaderCards() {
        String path = "src/main/resources/json/leaderCards.json";
        List<LeaderCard> cards = new ArrayList<>();

        Gson gson = new Gson();
        try {
            JsonReader reader = new JsonReader(new FileReader(path));

            Type listType = new TypeToken<List<LeaderCard>>() {}.getType();
            cards = gson.fromJson(reader, listType);
            return cards;
        } catch (FileNotFoundException e) {
            // mandare messaggio al client "Configuration file not found"
            return null;
        }
    }


    private void activateVaticanReport(int vatican_index) {
        int start = currPlayer.getPersonalBoard().getFaithTrack().getSections()[vatican_index].getStartSpace();

        for(Player p: playerList)
        {
            int position = p.getPersonalBoard().getFaithTrack().getFaithMarker();
            if(position >= start)
                p.getPersonalBoard().getFaithTrack().getSections()[vatican_index].setValidFavorTiles(StateFavorTiles.FACEUP);
            else p.getPersonalBoard().getFaithTrack().getSections()[vatican_index].setValidFavorTiles(StateFavorTiles.DISCARDED);
        }

    }

    //TODO handleError method

    //TODO handleConclusion method

    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    public Turn getTurn() {
        return turn;
    }

    public MarketTray getMarketTray() {
        return marketTray;
    }

    public List<Deck> getDecks() {
        return decks;
    }

    @Override
    public void update(Integer message)
    {
        activateVaticanReport(message);
    }


}

