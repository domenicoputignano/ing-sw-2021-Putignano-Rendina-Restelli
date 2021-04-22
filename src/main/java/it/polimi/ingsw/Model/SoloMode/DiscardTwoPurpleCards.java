package it.polimi.ingsw.Model.SoloMode;

import it.polimi.ingsw.Model.Card.ColorCard;

public class DiscardTwoPurpleCards implements TokenEffect{
    public void performTokenEffect(LorenzoIlMagnifico lorenzoIlMagnifico) {
        lorenzoIlMagnifico.throwDevCards(ColorCard.purple);
    }
}
