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

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This is the main class of the Model of the MVC Architectural Pattern.
 * It represents a game, containing all the players playing in it and all the other parts of Maestri del Rinascimento board game.
 * It receives orders from the Controllers and modifies itself, then he is responsible for the update of every remote view
 * of each player in the match, which is realised through the Observable interface.
 * It also is notified by parts of the model when specific events occur, for example when a Conclusion Event occurs.
 * Finally this class is responsible for the setup of the game structures.
 */

public abstract class Game extends Observable<ServerMessage> implements Observer<GameEvent> {
    /**
     * The player who owns the inkwell will be the first to begin in the first turn.
     */
    protected Player inkwell;
    /**
     * The player in turn.
     */
    protected Player currPlayer;
    /**
     * The list of all the players that are playing in this match.
     */
    protected List<Player> playerList;
    /**
     * The link between {@link User} and his identity as a {@link Player} in the game.
     */
    protected Map<User,Player> users;
    /**
     * The twelve decks of {@link DevelopmentCard} of Maestri del Rinascimento board game.
     */
    protected List<Deck> decks;
    /**
     * The state of the game.
     */
    protected GameState gameState;
    /**
     * The turn logic of the game.
     */
    protected Turn turn;
    /**
     * The Market Tray of Maestri del Rinascimento board game.
     */
    protected MarketTray marketTray;
    /**
     * Random used to generate random sequences of objects.
     */
    private final Random rand = new Random();

    /**
     * Setups the game by initializing each part of the model.
     * Also initializes the observer pattern structure, adding this class to the list of observers of every
     * part of the model which has to notify specific {@link GameEvent}.
     */
    public void setup(){
        this.marketTray = new MarketTray();
        this.users = new HashMap<>();
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
    }

    protected abstract void notifyGameSetup();

    /**
     * Initializes all the decks of {@link DevelopmentCard} parsing them by a JSON resource file.
     * Then shuffles them.
     */
    private void initializeDecksDevCards() {
        String path = "/json/devCards.json";
        decks = new ArrayList<>();
        Gson gson = new Gson();

        JsonReader reader = new JsonReader(new InputStreamReader(Objects.requireNonNull(Game.class.getResourceAsStream(path)), StandardCharsets.UTF_8));
        Type listType = new TypeToken<List<Deck>>() {
        }.getType();
        decks = gson.fromJson(reader, listType);
        shuffleDecksDevCards();
    }

    /**
     * Initializes Leader Cards and then deals four of them to each player.
     */
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

    /**
     * Proceeds to the next turn.
     */
    public abstract void nextTurn();

    /**
     * Utility method used to shuffle every {@link Deck} before the start of every match.
     */
    private void shuffleDecksDevCards() {
        for (Deck d : decks) {
            Collections.shuffle(d.getCards());
        }
    }

    /**
     * Initializes all Leader Cards parsing them from a JSON resource file.
     * @return the list of Leader Cards initialized.
     */
    private List<LeaderCard> initializeDeckLeaderCards() {
        String path = "/json/leaderCards.json";
        List<LeaderCard> cards = new ArrayList<>();

        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new InputStreamReader(Objects.requireNonNull(Game.class.getResourceAsStream(path)), StandardCharsets.UTF_8));

        Type listType = new TypeToken<List<LeaderCard>>() {}.getType();
        cards = gson.fromJson(reader, listType);
        return cards;
    }

    public abstract void activateVaticanReport(Player triggeringPlayer,int vatican_index);

    /**
     * Utility method used to check whether the deck of the specified {@link CardType} is empty.
     * @param cardType the card type whose deck has to be checked.
     * @return the check validity.
     */
    public boolean isEmptyDeck(CardType cardType) {
        return this.decks.stream().anyMatch(x -> x.getCardType().equals(cardType) && x.getSize()<=0);
    }

    /**
     * Method called when the HitLastSpace {@link ConclusionEvent} has been triggered.
     * {@link MultiPlayerMode} and {@link it.polimi.ingsw.Model.SoloMode.SoloMode} override this method with different
     * effects according to game rules.
     * @param event the event triggered.
     */
    public abstract void endGame(HitLastSpace event);

    /**
     * Method called when the DevCardColorEnded {@link ConclusionEvent} has been triggered.
     * Only {@link it.polimi.ingsw.Model.SoloMode.SoloMode} overrides this method since in {@link MultiPlayerMode}
     * this event doesn't end the game.
     * @param event the event triggered.
     */
    public void endGame(DevCardColorEnded event){}

    /**
     * Method called when the SeventhCardBough {@link ConclusionEvent} has been triggered.
     * {@link MultiPlayerMode} and {@link it.polimi.ingsw.Model.SoloMode.SoloMode} override this method with different
     * effects according to game rules.
     * @param event the event triggered.
     */
    public abstract void endGame(SeventhDevCardBought event);

    /**
     * Method called when the BlackCrossHitLastSpace {@link ConclusionEvent} has been triggered.
     * Only {@link it.polimi.ingsw.Model.SoloMode.SoloMode} overrides this method since in {@link MultiPlayerMode}
     * this event can't occur.
     * @param event the event triggered.
     */
    public void endGame(BlackCrossHitLastSpace event){}

    /**
     * Observer's update method implementation. The game is notified of a GameEvent and handles it by calling
     * the handleEvent method to the event itself, which has a different implementation depending on the Game Event.
     * @param event the game is notified of.
     */
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

    /**
     * Moves all the players in game except for the given triggering player by the given number of spaces.
     * @param triggeringPlayer the player who discarded the resources and doesn't have to be moved.
     * @param discardedResources the number of spaces to move the players by.
     */
    public abstract void moveOtherPlayers(Player triggeringPlayer, int discardedResources);

    public abstract void handlePlayerDisconnection(Player disconnectedPlayer);

    public abstract void handlePlayerReconnection(User reconnectingUser);

    /**
     * Notifies each remote view of the player in game when an update must be sent to client.
     * @param message the update to send.
     */
    public void notifyUpdate(UpdateMessage message) {
        notify(message);
    }

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

    /**
     * Searches a deck associated to the given card type and returns it.
     * @param type the card type to search
     * @return the deck, if found
     */
    public Deck searchDeck(CardType type) {
        if(getDecks().stream().anyMatch(x -> x.getCardType().equals(type)))
            return decks.stream().filter(x -> x.getCardType().equals(type)).collect(Collectors.toList()).get(0);
        else return null;
    }

    /**
     * Proceeds to the given {@link GameState}.
     * @param state the state to proceed to.
     */
    public void nextState(GameState state) {  this.gameState = state; }

    /**
     * Notifies each remote view of the players in game when a new turn begins.
     * @param message the update of the beginning of a new turn.
     */
    public void notifyTurn(NewTurnUpdate message) {
        notify(message);
    }

    /**
     * Notifies each remote view of the players in game when a player reconnects to the game.
     * @param savedUserInstance the saved user instance of the player that has reconnected.
     */
    public void notifyGameResumed(User savedUserInstance) {
        notify(new GameResumedMessage(this.getReducedVersion(), savedUserInstance, currPlayer.getUser()));
    }

    /**
     * Notifies each remote view of the players in game when a player reconnects to the game.
     * @param savedUserInstance the saved user instance of the player that has reconnected.
     * @param userInTurn the player in turn to notify to the reconnecting player.
     */
    public void notifyGameResumed(User savedUserInstance, User userInTurn) {
        notify(new GameResumedMessage(this.getReducedVersion(), savedUserInstance, userInTurn));
    }

    /**
     * @return the current {@link GameState} of the game.
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * @return the number of players in this game.
     */
    public int getNumOfPlayers(){
        return playerList.size();
    }

    /**
     * Retrieves the player instance corresponding to the given {@link User}.
     * @param user the user identity of the player to return.
     * @return the player instance corresponding to the given User.
     */
    public Player getPlayer(User user) {
        return users.get(user);
    }

    /**
     * @return a simplified version of this class that can be sent through the network to the client.
     */
    public abstract ReducedGame getReducedVersion();

    /**
     * Notifies each remote view of the players in game when the current player makes a mistake.
     * @param message the error message to notify.
     */
    public void notifyError(ErrorMessage message) {
        notify(message);
    }

}
