package it.polimi.ingsw.model.soloMode;

public class MoveBlackCrossByTwo implements TokenEffect{
    /**
     * Moves Lorenzo il Magnifico forward by two positions on the Faith Track.
     * @param lorenzoIlMagnifico the instance of Lorenzo il Magnifico.
     */
    public void performTokenEffect(LorenzoIlMagnifico lorenzoIlMagnifico) {
        lorenzoIlMagnifico.moveBlackCross(2);
    }

    @Override
    public String renderTokenImage() {
        return "gui/img/tokens/moveBy2.png";
    }

    public String renderTokenEffect() {
        return "Lorenzo moved his black cross forward by two spaces in the faith track";
    }
}
