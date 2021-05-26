package it.polimi.ingsw.Model.SoloMode;

import java.io.Serializable;

public interface TokenEffect extends Serializable {
    void performTokenEffect(LorenzoIlMagnifico lorenzoIlMagnifico);
    String renderTokenEffect();
}
