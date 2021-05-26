package it.polimi.ingsw.Model.SoloMode;

import java.io.Serializable;

public class Token implements Serializable {
    private final TokenEffect tokenEffect;

    public Token(TokenEffect tokenEffect) {
        this.tokenEffect = tokenEffect;
    }

    public void performEffect(LorenzoIlMagnifico lorenzoIlMagnifico) {
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
