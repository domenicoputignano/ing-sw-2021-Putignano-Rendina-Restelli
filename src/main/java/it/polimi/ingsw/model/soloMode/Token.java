package it.polimi.ingsw.model.soloMode;

import java.io.Serializable;

/**
 * This class represents the tokens played by Lorenzo il Magnifico in {@link SoloMode}.
 * It implements {@link Serializable} in order to be sent through the network.
 */
public class Token implements Serializable {
    /**
     * The {@link TokenEffect} associated with this token.
     */
    private final TokenEffect tokenEffect;

    /**
     * Constructs a token with the given token effect.
     * @param tokenEffect the token effect of the token to construct.
     */
    public Token(TokenEffect tokenEffect) {
        this.tokenEffect = tokenEffect;
    }

    /**
     * Performs the effect of this token by calling the proper method on the associated
     * {@link TokenEffect} instance.
     * @param lorenzoIlMagnifico the instance of Lorenzo il Magnifico.
     */
    public void performEffect(LorenzoIlMagnifico lorenzoIlMagnifico) {
        tokenEffect.performTokenEffect(lorenzoIlMagnifico);
    }

    /**
     * @return the effect of this token as a string.
     */
    @Override
    public String toString() {
        return  "{tokenEffect=" + tokenEffect +
                '}';
    }

    /**
     * @return the token effect associated with this token.
     */
    public TokenEffect getTokenEffect() {
        return tokenEffect;
    }
}
