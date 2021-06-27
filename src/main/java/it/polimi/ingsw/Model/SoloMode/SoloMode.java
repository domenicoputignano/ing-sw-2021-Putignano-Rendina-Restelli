package it.polimi.ingsw.Model.SoloMode;

import it.polimi.ingsw.Client.reducedmodel.ReducedGame;
import it.polimi.ingsw.Client.reducedmodel.ReducedMarketTray;
import it.polimi.ingsw.Client.reducedmodel.ReducedPlayer;
import it.polimi.ingsw.Client.reducedmodel.ReducedSoloMode;
import it.polimi.ingsw.Commons.StateFavorTiles;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Model.ConclusionEvents.*;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.GameState;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Turn;
import it.polimi.ingsw.Utils.Messages.ServerMessages.GameSetupMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.SoloModeMatchWinnerMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class extends the main class of the model {@link Game}, implementing one of the two possible
 * modalities of Maestri del Rinascimento board game. This mode is played only by one player versus
 * Lorenzo il Magnifico, who makes his moves by drawing tokens and activating their effects.
 */
public class SoloMode extends Game {
    /**
     * The stack of tokens where Lorenzo il Magnifico draws at each turn.
     */
    private Stack<Token> tokens = new Stack<>();
    /**
     * The Lorenzo il Magnifico instance.
     */
    private final LorenzoIlMagnifico lorenzoIlMagnifico;

    /**
     * Constructs a solo mode game instance from the given player.
     * Then starts the setup phase.
     * @param player the player who will play the game.
     */
    public SoloMode(Player player) {
        this.playerList = Collections.singletonList(player);
        this.inkwell = player;
        this.currPlayer = player;
        this.gameState = GameState.INITIALCHOICES;
        this.lorenzoIlMagnifico = new LorenzoIlMagnifico(this.getCurrPlayer().getPersonalBoard().getFaithTrack(), this);
        this.refreshTokens();
        this.setup();
    }

    /**
     * Refresh the token stack to the initial state and shuffles them.
     */
    public void refreshTokens() {
        this.tokens = new Stack<>();
        tokens.push(new Token(new DiscardTwoBlueCards()));
        tokens.push(new Token(new DiscardTwoYellowCards()));
        tokens.push(new Token(new DiscardTwoGreenCards()));
        tokens.push(new Token(new DiscardTwoPurpleCards()));
        tokens.push(new Token(new MoveBlackCrossAndShuffle()));
        tokens.push(new Token(new MoveBlackCrossByTwo()));
        tokens.push(new Token(new MoveBlackCrossByTwo()));
        Collections.shuffle(tokens);
    }

    /**
     * Proceeds to the next turn after Lorenzo draws and plays his token.
     * The player is notified of Lorenzo's move.
     */
    public void nextTurn() {
        Token playedToken = lorenzoPlays();
        this.turn = new Turn(this, this.currPlayer);
        notify(new LorenzoPlayedUpdate(currPlayer.getReducedPersonalBoard(), playedToken,
                tokens, decks, lorenzoIlMagnifico.getBlackCross()));
    }

    @Override
    public void handlePlayerDisconnection(Player disconnectedPlayer) {
        //TODO sospendere il gioco.
    }

    /**
     * Implementation of the method called when {@link HitLastSpace} conclusion event is reached.
     * In Solo Mode when the player reaches the last space, he automatically wins.
     * @param event the event triggered.
     */
    @Override
    public void endGame(HitLastSpace event) {
        concludeGame(true,event);
    }

    /**
     * Implementation of the method called when {@link SeventhDevCardBought} conclusion event is reached.
     * In Solo Mode when the player buys his seventh card, he automatically wins.
     * @param event the event triggered.
     */
    @Override
    public void endGame(SeventhDevCardBought event) {
        concludeGame(true,event);
    }

    /**
     * Implementation of the method called when {@link DevCardColorEnded} conclusion event is reached.
     * In Solo Mode when a dev card color ends, the player automatically looses.
     * @param event the event triggered.
     */
    @Override
    public void endGame(DevCardColorEnded event) {
        concludeGame(false,event);
    }

    /**
     * Implementation of the method called when {@link BlackCrossHitLastSpace} conclusion event is reached.
     * In Solo Mode when Lorenzo il Maginifico reaches the last space, the player automatically looses.
     * @param event the event triggered.
     */
    @Override
    public void endGame(BlackCrossHitLastSpace event) {
        concludeGame(false,event);
    }

    /**
     * Moves Lorenzo il Magnifico by a number of positions equals to the number of discarded resources.
     * @param triggeringPlayer the player who discarded the resources and doesn't have to be moved.
     * @param discardedResources the number of spaces to move Lorenzo il Magnifico by.
     */
    @Override
    public void moveOtherPlayers(Player triggeringPlayer, int discardedResources) {
        boolean hasBeenActivatedAReport = lorenzoIlMagnifico.moveBlackCross(discardedResources);
        notify(new BlackCrossMoveUpdate(this.lorenzoIlMagnifico.getBlackCross(), hasBeenActivatedAReport));
    }

    /**
     * Method called when a vatican report is activated by Lorenzo il Magnifico on the given section.
     * The player checks his Faith Track: if his Faith Marker is on a space within or beyond the activated
     * Vatican Report section, turn the Pope's Favor tile of that section face-up, else discard the Pope's
     * Favor tile from that section.
     * Then notifies his remote view of the overcome of this Vatican Report activation by Lorenzo il Magnifico.
     * @param vaticanIndex the section index which the vatican report has been activated on.
     */
    public void LorenzoActivatedAVaticanReport(int vaticanIndex) {
        int firstUsefulPosition = currPlayer.getPersonalBoard().getFaithTrack().getSections()[vaticanIndex].getStartSpace();
        int userPosition = currPlayer.getPersonalBoard().getFaithTrack().getFaithMarker();
        if(userPosition >= firstUsefulPosition) {
            currPlayer.getPersonalBoard().getFaithTrack().setFavorTile(vaticanIndex, StateFavorTiles.FACEUP);
        } else currPlayer.getPersonalBoard().getFaithTrack().setFavorTile(vaticanIndex, StateFavorTiles.DISCARDED);
        notify(new LorenzoActivatedVaticanReportUpdate(currPlayer.getUser(), currPlayer.getReducedPersonalBoard(),
                vaticanIndex+1, currPlayer.getPersonalBoard().getFaithTrack().getStateFavorTile(vaticanIndex)));
    }

    /**
     * Method called when a vatican report is activated by the player on the given section.
     * This method is called only if the correspondent Favor Tile is FACEDOWN. Since this means that
     * Lorenzo il Magnifico has not activated the vatican report on this section yet, automatically
     * turns the correspondent Favor Tile face up.
     * Then notifies the remote view of the overcome of this Vatican Report activation.
     * @param triggeringPlayer the player who activated the Vatican Report.
     * @param vatican_index the section index which the vatican report has been activated on.
     */
    public void activateVaticanReport(Player triggeringPlayer,int vatican_index) {
        triggeringPlayer.getPersonalBoard().getFaithTrack().setFavorTile(vatican_index,StateFavorTiles.FACEUP);
        notify(new ActivateVaticanReportUpdate(currPlayer.getUser(),currPlayer.getReducedPersonalBoard(),
                currPlayer.getUser(),currPlayer.getPersonalBoard().getFaithTrack().getStateFavorTile(vatican_index),vatican_index));
    }

    /**
     * Notifies the remote view of the player in game when the game has been setup by sending him
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
    @Override
    public ReducedGame getReducedVersion() {
        List<ReducedPlayer> players = playerList.stream().map(Player::getReducedVersion).collect(Collectors.toList());
        ReducedMarketTray marketTray = this.marketTray.getReducedVersion();
        return new ReducedSoloMode(players, decks, marketTray, tokens, lorenzoIlMagnifico.getBlackCross());
    }

    /**
     * Performs the Lorenzo il Magnifico's action by drawing a token from the stack,
     * revealing it and activating its effect.
     * @return the token played.
     */
    public Token lorenzoPlays() {
        Token playedToken = tokens.pop();
        playedToken.performEffect(lorenzoIlMagnifico);
        return playedToken;
    }

    /**
     * @return the token on top of the stack.
     */
    public Token peekToken() {
        return tokens.peek();
    }

    /**
     * @return the stack of tokens.
     */
    public Stack<Token> getTokens() {
        return tokens;
    }

    /**
     * @return the instance of Lorenzo il Magnifico associated with this class.
     */
    public LorenzoIlMagnifico getLorenzoIlMagnifico() {
        return lorenzoIlMagnifico;
    }

    /**
     * Conclude the game and notifies the player with the outcome of the game and the score he obtained.
     * @param playerWon whether the player won.
     * @param event the conclusion event that triggered the game conclusion.
     */
    public void concludeGame(boolean playerWon, ConclusionEvent event) {
        notify(new SoloModeMatchWinnerMessage(playerWon, currPlayer.calcVictoryPointsPlayer(),event));
    }

    /**
     * Handles the reconnection of the player.
     * Resumes the game which was paused and notify it to the player.
     * @param reconnectingUser the user who reconnected.
     */
    @Override
    public void handlePlayerReconnection(User reconnectingUser) {
        notifyGameResumed(reconnectingUser);
        if(turn.isDoneNormalAction()) {
            nextTurn();
        }
    }
}
