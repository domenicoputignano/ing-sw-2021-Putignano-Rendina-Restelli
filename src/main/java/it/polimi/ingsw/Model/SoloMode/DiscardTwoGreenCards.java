package it.polimi.ingsw.Model.SoloMode;

import it.polimi.ingsw.Exceptions.EndGameException;
import it.polimi.ingsw.Commons.ColorCard;

public class DiscardTwoGreenCards implements TokenEffect{
    public void performTokenEffect(LorenzoIlMagnifico lorenzoIlMagnifico) throws EndGameException {
        lorenzoIlMagnifico.throwDevCards(ColorCard.green);
    }
}
