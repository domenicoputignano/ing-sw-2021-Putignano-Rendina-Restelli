package it.polimi.ingsw.model.soloMode;

import it.polimi.ingsw.commons.ColorCard;

public class DiscardTwoPurpleCards implements TokenEffect{
    /**
     * Discards two purple cards from the decks.
     * @param lorenzoIlMagnifico the instance of Lorenzo il Magnifico.
     */
    public void performTokenEffect(LorenzoIlMagnifico lorenzoIlMagnifico) {
        lorenzoIlMagnifico.throwDevCards(ColorCard.purple);
    }

    @Override
    public String renderTokenImage() {
        return "gui/img/tokens/discard2purple.png";
    }

    @Override
    public String renderTokenEffect() {
        return "Lorenzo discarded two purple cards from the decks";
    }
}
