package it.polimi.ingsw.Model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.Model.Card.Deck;
import it.polimi.ingsw.Model.Card.LeaderCard;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Game {
    private Player inkwell;
    private List<Player> playerList;
    private Player currPlayer;
    private List<Deck> decks;
    private int numOfPlayers;

    public void VaticanReport (int vaticanIndex) {

    }

    public Game(Player inkwell, List<Player> playerList, Player currPlayer, int numOfPlayers) throws FileNotFoundException {
        this.inkwell = inkwell;
        this.playerList = playerList;
        this.currPlayer = currPlayer;
        this.numOfPlayers = numOfPlayers;
        this.factoryDecksDevCards();
    }

    public Player getWinner() {
        /*da implementare*/
       return null;
    }

    public int calcVictoryPoints() {
        /*da implementare*/
        return 1;
    }

    private void factoryDecksDevCards() throws FileNotFoundException {
        String path = "src/main/resources/json/devCards.json";
        decks = new ArrayList<>();

        Gson gson = new Gson();

        JsonReader reader = new JsonReader(new FileReader(path));

        Type listType = new TypeToken<List<Deck>>() {}.getType();
        decks = gson.fromJson(reader, listType);
        shuffleDecksDevCards();
    }

    private void shuffleDecksDevCards(){
        for (Deck d : decks){
            Collections.shuffle(d.getCards());
        }
    }

    private List<LeaderCard> factoryDeckLeaderCards(){
        String path = "src/main/resources/json/leaderCards.json";
        List<LeaderCard> cards = new ArrayList<>();

        Gson gson = new Gson();
        try {
            JsonReader reader = new JsonReader(new FileReader(path));

            Type listType = new TypeToken<List<LeaderCard>>() {
            }.getType();
            cards = gson.fromJson(reader, listType);
            return cards;
        }
        catch (FileNotFoundException e){
            // mandare messaggio al client "Configuration file not found"
            return null;
        }
    }

    public void dealLeaderCards(){
        List<LeaderCard> cards = factoryDeckLeaderCards();
        Random rand = new Random();
        for(Player p : playerList){
            for(int i=0; i<4; i++){
                int randNum = rand.nextInt(cards.size());
                LeaderCard card = cards.remove(randNum);
                p.getLeaderCards().add(card);
            }
        }
    }

}
