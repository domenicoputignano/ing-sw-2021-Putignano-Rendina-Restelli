package it.polimi.ingsw.Model.SoloMode;

public class MoveBlackCrossByTwo implements TokenEffect{
    public void performTokenEffect(LorenzoIlMagnifico lorenzoIlMagnifico) {
        lorenzoIlMagnifico.moveBlackCross(2);
    }
    public String renderTokenEffect() {
        return "Lorenzo moved his black cross forward by two spaces in the faith track";
    }
}
