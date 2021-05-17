package it.polimi.ingsw.Model.SoloMode;

import it.polimi.ingsw.Exceptions.EndGameException;

import java.io.Serializable;

public class Token implements Serializable {
    private TokenEffect tokenEffect;

    public Token(TokenEffect tokenEffect) {
        this.tokenEffect = tokenEffect;
    }

    public void performEffect(LorenzoIlMagnifico lorenzoIlMagnifico) throws EndGameException {
        tokenEffect.performTokenEffect(lorenzoIlMagnifico);
    }

    @Override
    public String toString() {
        return  "{tokenEffect=" + tokenEffect +
                '}';
    }

    public TokenEffect getTokenEffect() {
        return tokenEffect;
    }
}
