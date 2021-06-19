package it.polimi.ingsw.Model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.Client.reducedmodel.ReducedGame;
import it.polimi.ingsw.Commons.*;
import it.polimi.ingsw.Model.ConclusionEvents.*;
import it.polimi.ingsw.Model.MarketTray.MarketTray;
import it.polimi.ingsw.Observable;
import it.polimi.ingsw.Observer;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Errors.ErrorMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.GameResumedMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.NewTurnUpdate;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.UpdateMessage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;



public abstract class Game extends Observable<ServerMessage> implements Observer<GameEvent> {
    protected Player inkwell;
    protected Player currPlayer;
    protected List<Player> playerList;
    protected Map<User,Player> users;
    protected List<Deck> decks;
    protected GameState gameState;
    protected Turn turn;
    protected MarketTray marketTray;
    private Random rand = new Random();

    public void setup(){
        this.marketTray = new MarketTray();
        this.users = new HashMap<User, Player>();
        this.initializeDecksDevCards();
        this.dealLeaderCards();
        for(Player p : this.playerList) {
            p.setPosition(playerList.indexOf(p) + 1);
            users.put(p.getUser(), p);
            /*aggiungo MultiPlayerMode alla lista di Observer di faithtrack per la vatican report section*/
            p.getPersonalBoard().addObserver(this);
            p.getPersonalBoard().getFaithTrack().addObserver(this);
            if (p.getPosition() == 3 || p.getPosition() == 4) {
                p.getPersonalBoard().moveMarker(p, 1);
            }
        }
        this.turn = new Turn(this,inkwell);
        //DISTRIBUZIONE RISORSE A SCELTA E SCELTA CARTE LEADER PASSANDO IN RESOURCECHOICE E LEADER CHOICE
        //Inviare notifica alle view interessate per le risorse a scelta
    }



    protected abstract void notifyGameSetup();

    private void initializeDecksDevCards() {
        String path = "/json/devCards.json";
        decks = new ArrayList<>();
        Gson gson = new Gson();

        JsonReader reader = new JsonReader(new InputStreamReader(Game.class.getResourceAsStream(path), StandardCharsets.UTF_8));
        //JsonReader reader = new JsonReader(new InputStreamReader(Objects.requireNonNull(decks.getClass().getResourceAsStream(path)), StandardCharsets.UTF_8));
        Type listType = new TypeToken<List<Deck>>() {
        }.getType();
        decks = gson.fromJson(reader, listType);
        shuffleDecksDevCards();
    }

    private void dealLeaderCards() {
        List<LeaderCard> cards = initializeDeckLeaderCards();
        for (Player p : playerList) {
            for (int i = 0; i < 4; i++) {
                int randNum = rand.nextInt(cards.size());
                LeaderCard card = cards.remove(randNum);
                p.getLeaderCards().add(card);
            }
        }
    }

    public abstract void nextTurn();


    private void shuffleDecksDevCards() {
        for (Deck d : decks) {
            Collections.shuffle(d.getCards());
        }
    }

    private List<LeaderCard> initializeDeckLeaderCards() {
        String path = "/json/leaderCards.json";
        List<LeaderCard> cards = new ArrayList<>();

        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new InputStreamReader(Game.class.getResourceAsStream(path), StandardCharsets.UTF_8));

        Type listType = new TypeToken<List<LeaderCard>>() {}.getType();
        cards = gson.fromJson(reader, listType);
        return cards;
    }


    //TODO da cambiare
    public abstract void activateVaticanReport(Player triggeringPlayer,int vatican_index) ;



    public boolean isEmptyDeck(CardType cardType) {
        return this.decks.stream().anyMatch(x -> x.getCardType().equals(cardType) && x.getSize()<=0);
    }

    public abstract void endGame(HitLastSpace event);

    public void endGame(DevCardColorEnded event){}

    public abstract void endGame(SeventhDevCardBought event);

    public void endGame(BlackCrossHitLastSpace event){}

    @Override
    public void update(GameEvent event) {
        event.handleEvent(this);
    }

    public Player getCurrPlayer() {
        return currPlayer;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public abstract void moveOtherPlayers(Player triggeringPlayer, int discardedResources);

    public abstract void handlePlayerDisconnection(Player disconnectedPlayer);

    public abstract void handlePlayerReconnection(User reconnectingUser);

    public void notifyUpdate(UpdateMessage message) {
        notify(message);
    }


    //TODO handleEvent method

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

    public void nextState(GameState state) {  this.gameState = state; }

    public void notifyTurn(NewTurnUpdate message) {
        notify(message);
    }

    public void notifyGameResumed(User savedUserInstance) {
        notify(new GameResumedMessage(this.getReducedVersion(), savedUserInstance, currPlayer.getUser()));
    }

    public void notifyGameResumed(User savedUserInstance, User userInTurn) {
        notify(new GameResumedMessage(this.getReducedVersion(), savedUserInstance, userInTurn));
    }

    public GameState getGameState() {
        return gameState;
    }


    public int getNumOfPlayers(){
        return playerList.size();
    }


    public Player getPlayer(User user) {
        return users.get(user);
    }

    public abstract ReducedGame getReducedVersion();

    public void notifyError(ErrorMessage message) {
        notify(message);
    }

}
