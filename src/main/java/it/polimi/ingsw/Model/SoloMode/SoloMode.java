package it.polimi.ingsw.Model.SoloMode;

import java.util.Collections;
import java.util.Stack;

public class SoloMode {
    Stack<Token> tokens = new Stack<Token>();

    public SoloMode(Stack<Token> tokens) {
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
