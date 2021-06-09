package it.polimi.ingsw.Model.SoloMode;

import it.polimi.ingsw.Commons.ColorCard;

public class DiscardTwoBlueCards implements TokenEffect {
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
