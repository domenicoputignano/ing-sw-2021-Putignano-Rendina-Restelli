package it.polimi.ingsw.Model.SoloMode;


public class MoveBlackCrossAndShuffle implements TokenEffect{
    public void performTokenEffect(LorenzoIlMagnifico lorenzoIlMagnifico) {
        lorenzoIlMagnifico.moveAndShuffle();
    }
    public String renderTokenEffect() {
        return "Lorenzo moved his black cross forward by one space on the faith track and shuffled tokens";
    }
}
