package it.polimi.ingsw.Model.SoloMode;

public class MoveBlackCrossAndShuffle implements TokenEffect{
    public void performTokenEffect(LorenzoIlMagnifico lorenzoIlMagnifico) {
        lorenzoIlMagnifico.moveBlackCross(1);
        lorenzoIlMagnifico.moveAndShuffle();
    }
}
