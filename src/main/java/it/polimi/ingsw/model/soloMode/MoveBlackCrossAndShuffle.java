package it.polimi.ingsw.model.soloMode;


public class MoveBlackCrossAndShuffle implements TokenEffect{
    /**
     * Moves Lorenzo il Magnifico forward by one space on the Faith Track.
     * Then shuffles the token stack.
     * @param lorenzoIlMagnifico the instance of Lorenzo il Magnifico.
     */
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
