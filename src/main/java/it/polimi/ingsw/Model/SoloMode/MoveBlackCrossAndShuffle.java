package it.polimi.ingsw.Model.SoloMode;

import it.polimi.ingsw.Exceptions.EndGameException;

public class MoveBlackCrossAndShuffle implements TokenEffect{
    public void performTokenEffect(LorenzoIlMagnifico lorenzoIlMagnifico) throws EndGameException {
        lorenzoIlMagnifico.moveBlackCross(1);
        lorenzoIlMagnifico.moveAndShuffle();
    }
}
