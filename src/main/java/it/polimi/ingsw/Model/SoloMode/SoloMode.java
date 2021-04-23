package it.polimi.ingsw.Model.SoloMode;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.MultiPlayerMode;
import it.polimi.ingsw.Model.Player;

import java.util.Collections;
import java.util.Stack;

public class SoloMode extends Game {
    Stack<Token> tokens = new Stack<Token>();


    public SoloMode(Player player,Stack<Token> tokens) {
        this.refreshTokens();
    }

    public Token draw()
    {
        return this.tokens.pop();
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
        Collections.shuffle(tokens);
    }


}
