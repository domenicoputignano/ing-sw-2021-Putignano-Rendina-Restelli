package it.polimi.ingsw.Model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.Model.Card.CardType;
import it.polimi.ingsw.Model.Card.Deck;
import it.polimi.ingsw.Model.Card.LeaderCard;
import it.polimi.ingsw.Model.ConclusionEvents.BlackCrossHitLastSpace;
import it.polimi.ingsw.Model.ConclusionEvents.DevCardColorEnded;
import it.polimi.ingsw.Model.ConclusionEvents.HitLastSpace;
import it.polimi.ingsw.Model.ConclusionEvents.SeventhDevCardBought;
import it.polimi.ingsw.Model.MarketTray.MarketTray;
import it.polimi.ingsw.Observer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public abstract class Game implements Observer<Integer> {
    protected Player inkwell;
    protected Player currPlayer;
    protected List<Player> playerList;
    protected List<Deck> decks;
    protected GameState gameState;
    protected Turn turn;
    protected MarketTray marketTray;

    public void setup()
    {
        this.marketTray = new MarketTray();
        this.initializeDecksDevCards();
        this.dealLeaderCards();
        for(Player p : this.playerList)
        {
            p.setPosition(playerList.indexOf(p)+1);

            /*aggiungo MultiPlayerMode alla lista di Observer di faithtrack per la vatican report section*/
            p.getPersonalBoard().getFaithTrack().addObserver(this);

            if(p.getPosition()==3 || p.getPosition()==4)
                p.getPersonalBoard().getFaithTrack().moveMarker(1);
        }
        this.turn = new Turn(this,inkwell);
        //DISTRIBUZIONE RISORSE A SCELTA E SCELTA CARTE LEADER PASSANDO IN RESOURCECHOICE E LEADER CHOICE
        //Inviare notifica alle view interessate per le risorse a scelta
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

    private void dealLeaderCards() {
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
                p.getPersonalBoard().getFaithTrack().setFavorTile(vatican_index,StateFavorTiles.FACEUP);
            else p.getPersonalBoard().getFaithTrack().setFavorTile(vatican_index,StateFavorTiles.DISCARDED);
        }

    }

    public boolean isEmptyDeck(CardType cardType)
    {
        return this.decks.stream().anyMatch(x -> x.getCardType().equals(cardType) && x.getSize()<=0);
    }

    public void endGame(HitLastSpace event){
        //throw UnsupportedOperationException
    }

    public void endGame(DevCardColorEnded event){
        //throw UnsupportedOperationException
    }

    public void endGame(SeventhDevCardBought event){
        //throw UnsupportedOperationException
    }

    public void endGame(BlackCrossHitLastSpace event){
        //throw UnsupportedOperationException
    }

    @Override
    public void update(Integer message)
    {
        activateVaticanReport(message);
    }

    public Player getCurrPlayer() {
        return currPlayer;
    }

    public List<Player> getPlayerList() {
        return playerList;
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
        //TODO : DA MODIFICARE
    }

    public Deck searchDeck(CardType type) {
        if(getDecks().stream().anyMatch(x -> x.getCardType().equals(type)))
            return decks.stream().filter(x -> x.getCardType().equals(type)).collect(Collectors.toList()).get(0);
        else return null;
    }

    public void nextState(GameState state)
    {
        this.gameState = state;
    }

    public GameState getGameState() {
        return gameState;
    }
}
