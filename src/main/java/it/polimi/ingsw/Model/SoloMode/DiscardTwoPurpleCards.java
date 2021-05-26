package it.polimi.ingsw.Model.SoloMode;

import it.polimi.ingsw.Commons.ColorCard;

public class DiscardTwoPurpleCards implements TokenEffect{
    public void performTokenEffect(LorenzoIlMagnifico lorenzoIlMagnifico) {
        lorenzoIlMagnifico.throwDevCards(ColorCard.purple);
    }

    @Override
    public String renderTokenEffect() {
        return "Lorenzo discarded two purple cards from the decks";
    }
}
