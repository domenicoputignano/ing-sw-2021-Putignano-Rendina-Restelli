package it.polimi.ingsw.Model.SoloMode;

import it.polimi.ingsw.Exceptions.EndGameException;
import it.polimi.ingsw.Model.Card.ColorCard;

public class DiscardTwoYellowCards implements TokenEffect{
    public void performTokenEffect(LorenzoIlMagnifico lorenzoIlMagnifico) throws EndGameException {
        lorenzoIlMagnifico.throwDevCards(ColorCard.yellow);
    }
}
