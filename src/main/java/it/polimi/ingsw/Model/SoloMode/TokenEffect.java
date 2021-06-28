package it.polimi.ingsw.Model.SoloMode;

import java.io.Serializable;

/**
 * All the possible token effects implement this interface and override each method depending on
 * the different type of token.
 * It implements {@link Serializable} in order to be sent through the network.
 */
public interface TokenEffect extends Serializable {
    /**
     * Performs the effect of the token.
     * @param lorenzoIlMagnifico the instance of Lorenzo il Magnifico.
     */
    void performTokenEffect(LorenzoIlMagnifico lorenzoIlMagnifico);

    /**
     * @return a string description of this effect.
     */
    String renderTokenEffect();

    /**
     * @return the url string of the image of this token effect.
     */
    String renderTokenImage();
}
