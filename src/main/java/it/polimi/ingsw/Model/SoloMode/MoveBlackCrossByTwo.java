package it.polimi.ingsw.Model.SoloMode;

import it.polimi.ingsw.Exceptions.EndGameException;

public class MoveBlackCrossByTwo implements TokenEffect{
    public void performTokenEffect(LorenzoIlMagnifico lorenzoIlMagnifico) throws EndGameException {
        lorenzoIlMagnifico.moveBlackCross(2);
    }
}
