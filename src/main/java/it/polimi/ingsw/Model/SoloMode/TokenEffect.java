package it.polimi.ingsw.Model.SoloMode;

import it.polimi.ingsw.Exceptions.EndGameException;

public interface TokenEffect {
    void performTokenEffect(LorenzoIlMagnifico lorenzoIlMagnifico) throws EndGameException;

}
