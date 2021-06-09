package it.polimi.ingsw.Model.SoloMode;


public class MoveBlackCrossAndShuffle implements TokenEffect{
    public void performTokenEffect(LorenzoIlMagnifico lorenzoIlMagnifico) {
        lorenzoIlMagnifico.moveAndShuffle();
    }

    @Override
    public String renderTokenImage() {
        return "gui/img/tokens/moveBy1andShuffle.png";
    }

    public String renderTokenEffect() {
        return "Lorenzo moved his black cross forward by one space on the faith track and shuffled tokens";
    }
}
