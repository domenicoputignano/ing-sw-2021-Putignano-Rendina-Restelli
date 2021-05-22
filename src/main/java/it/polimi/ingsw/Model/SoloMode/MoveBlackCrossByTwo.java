package it.polimi.ingsw.Model.SoloMode;

public class MoveBlackCrossByTwo implements TokenEffect{
    public void performTokenEffect(LorenzoIlMagnifico lorenzoIlMagnifico) {
        lorenzoIlMagnifico.moveBlackCross(2);
    }
}
