package it.polimi.ingsw.model.soloMode;

import it.polimi.ingsw.commons.ColorCard;

public class DiscardTwoBlueCards implements TokenEffect {
    /**
     * Discards two blue cards from the decks.
     * @param lorenzoIlMagnifico the instance of Lorenzo il Magnifico.
     */
    public void performTokenEffect(LorenzoIlMagnifico lorenzoIlMagnifico) {
        lorenzoIlMagnifico.throwDevCards(ColorCard.blue);
    }

    @Override
    public String renderTokenImage() {
        return "gui/img/tokens/discard2blue.png";
    }

    @Override
    public String renderTokenEffect() {
        return "Lorenzo discarded two blue cards from the decks";
    }
}