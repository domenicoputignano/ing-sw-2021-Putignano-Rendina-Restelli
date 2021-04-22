package it.polimi.ingsw.Model.SoloMode;

import it.polimi.ingsw.Model.Card.ColorCard;
import it.polimi.ingsw.Model.MarketTray.Color;

public class DiscardTwoBlueCards implements TokenEffect{
    public void performTokenEffect(LorenzoIlMagnifico lorenzoIlMagnifico) {
        lorenzoIlMagnifico.throwDevCards(ColorCard.blue);
    }
}
