package it.polimi.ingsw.Model.SoloMode;

import it.polimi.ingsw.Exceptions.EndGameException;

import java.io.Serializable;

public interface TokenEffect extends Serializable {
    void performTokenEffect(LorenzoIlMagnifico lorenzoIlMagnifico) throws EndGameException;

}
