package it.polimi.ingsw.Model.SoloMode;

import it.polimi.ingsw.Model.Card.ColorCard;

public class DiscardTwoYellowCards implements TokenEffect{
    public void performTokenEffect(LorenzoIlMagnifico lorenzoIlMagnifico) {
        lorenzoIlMagnifico.throwDevCards(ColorCard.yellow);
    }
}
