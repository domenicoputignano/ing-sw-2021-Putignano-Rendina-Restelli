package it.polimi.ingsw.model.soloMode;

import it.polimi.ingsw.commons.ColorCard;

public class DiscardTwoYellowCards implements TokenEffect{
    /**
     * Discards two yellow cards from the decks.
     * @param lorenzoIlMagnifico the instance of Lorenzo il Magnifico.
     */
    public void performTokenEffect(LorenzoIlMagnifico lorenzoIlMagnifico) {
        lorenzoIlMagnifico.throwDevCards(ColorCard.yellow);
    }

    @Override
    public String renderTokenImage() {
        return "gui/img/tokens/discard2yellow.png";
    }

    @Override
    public String renderTokenEffect() {
        return "Lorenzo discarded two yellow cards from the decks";
    }


}
