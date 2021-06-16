package it.polimi.ingsw.Model.SoloMode;

import it.polimi.ingsw.Client.reducedmodel.ReducedGame;
import it.polimi.ingsw.Client.reducedmodel.ReducedMarketTray;
import it.polimi.ingsw.Client.reducedmodel.ReducedPlayer;
import it.polimi.ingsw.Client.reducedmodel.ReducedSoloMode;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Model.ConclusionEvents.*;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.GameState;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Turn;
import it.polimi.ingsw.Utils.Messages.ServerMessages.GameSetupMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.SoloModeMatchWinnerMessage;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.BlackCrossMoveUpdate;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.LorenzoPlayedUpdate;

import java.util.*;
import java.util.stream.Collectors;

public class SoloMode extends Game {
    private Stack<Token> tokens = new Stack<Token>();
    private final LorenzoIlMagnifico lorenzoIlMagnifico;

    public SoloMode(Player player) {
        this.playerList = Collections.singletonList(player);
        this.inkwell = player;
        this.currPlayer = player;
        this.gameState = GameState.LEADERCHOICE;
        lorenzoIlMagnifico = new LorenzoIlMagnifico(this.getCurrPlayer().getPersonalBoard().getFaithTrack(), this);
        this.refreshTokens();
        this.setup();
    }

    public void refreshTokens() {
        this.tokens = new Stack<Token>();
        tokens.push(new Token(new DiscardTwoBlueCards()));
        tokens.push(new Token(new DiscardTwoYellowCards()));
        tokens.push(new Token(new DiscardTwoGreenCards()));
        tokens.push(new Token(new DiscardTwoPurpleCards()));
        tokens.push(new Token(new MoveBlackCrossAndShuffle()));
        tokens.push(new Token(new MoveBlackCrossByTwo()));
        tokens.push(new Token(new MoveBlackCrossByTwo()));
        Collections.shuffle(tokens);
    }

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

    @Override
    public void endGame(HitLastSpace event) {
        concludeGame(true,event);
    }

    @Override
    public void endGame(SeventhDevCardBought event) {
        concludeGame(true,event);
    }

    @Override
    public void endGame(DevCardColorEnded event) {
        concludeGame(false,event);
    }

    @Override
    public void endGame(BlackCrossHitLastSpace event) {
        concludeGame(false,event);
    }

    @Override
    public void moveOtherPlayers(Player triggeringPlayer, int discardedResources) {
        lorenzoIlMagnifico.moveBlackCross(discardedResources);
        notify(new BlackCrossMoveUpdate(this.lorenzoIlMagnifico.getBlackCross()));
    }

    @Override
    public void notifyGameSetup() {
        notify(new GameSetupMessage(this.getReducedVersion()));
    }

    @Override
    public ReducedGame getReducedVersion() {
        List<ReducedPlayer> players = playerList.stream().map(Player::getReducedVersion).collect(Collectors.toList());
        ReducedMarketTray marketTray = this.marketTray.getReducedVersion();
        return new ReducedSoloMode(players, decks, marketTray, tokens);
    }

    public Token lorenzoPlays() {
        Token playedToken = tokens.pop();
        playedToken.performEffect(lorenzoIlMagnifico);
        return playedToken;
    }

    public Token peekToken() {
        return tokens.peek();
    }

    public Stack<Token> getTokens() {
        return tokens;
    }

    public LorenzoIlMagnifico getLorenzoIlMagnifico() {
        return lorenzoIlMagnifico;
    }

    public void concludeGame(boolean playerWon, ConclusionEvent event) {
        notify(new SoloModeMatchWinnerMessage(playerWon, currPlayer.calcVictoryPointsPlayer(),event));
    }

    @Override
    public void handlePlayerReconnection(User reconnectingUser) {
        notifyGameResumed(reconnectingUser);
    }
}
