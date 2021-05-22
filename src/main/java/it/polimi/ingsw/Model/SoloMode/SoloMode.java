package it.polimi.ingsw.Model.SoloMode;

import it.polimi.ingsw.Client.reducedmodel.ReducedGame;
import it.polimi.ingsw.Client.reducedmodel.ReducedMarketTray;
import it.polimi.ingsw.Client.reducedmodel.ReducedPlayer;
import it.polimi.ingsw.Client.reducedmodel.ReducedSoloMode;
import it.polimi.ingsw.Model.ConclusionEvents.HitLastSpace;
import it.polimi.ingsw.Model.ConclusionEvents.SeventhDevCardBought;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.GameState;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Utils.Messages.ServerMessages.GameSetupMessage;

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

    public void refreshTokens()
    {
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

    public void nextTurn(){
        lorenzoPlays();
        //TODO notifyUpdate(new LorenzoPlayedUpdate(...))
    }

    @Override
    public void endGame(HitLastSpace event) {

    }

    @Override
    public void endGame(SeventhDevCardBought event) {

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

    public void lorenzoPlays(){
        tokens.pop().performEffect(lorenzoIlMagnifico);
    }

    public Token peekToken()
    {
        return tokens.peek();
    }

    public Stack<Token> getTokens() {
        return tokens;
    }

    public LorenzoIlMagnifico getLorenzoIlMagnifico() {
        return lorenzoIlMagnifico;
    }
}
