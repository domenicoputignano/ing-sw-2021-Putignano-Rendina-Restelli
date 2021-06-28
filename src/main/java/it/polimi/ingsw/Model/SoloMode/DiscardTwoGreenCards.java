package it.polimi.ingsw.Model.SoloMode;

import it.polimi.ingsw.Commons.ColorCard;

public class DiscardTwoGreenCards implements TokenEffect{
    /**
     * Discards two green cards from the decks.
     * @param lorenzoIlMagnifico the instance of Lorenzo il Magnifico.
     */
    public void performTokenEffect(LorenzoIlMagnifico lorenzoIlMagnifico) {
        lorenzoIlMagnifico.throwDevCards(ColorCard.green);
    }

    @Override
    public String renderTokenImage() {
        return "gui/img/tokens/discard2green.png";
    }

    @Override
    public String renderTokenEffect() {
        return "Lorenzo discarded two green cards from the decks";
    }
}
