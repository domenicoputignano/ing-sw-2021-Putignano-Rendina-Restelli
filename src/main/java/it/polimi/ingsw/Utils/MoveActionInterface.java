package it.polimi.ingsw.Utils;

import it.polimi.ingsw.Model.Warehouse;

import java.io.Serializable;

public interface MoveActionInterface extends Serializable {

    public boolean handleMove(Warehouse warehouse);
    public boolean isValidMove();
}
