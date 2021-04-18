package it.polimi.ingsw.Utils;

import it.polimi.ingsw.Exceptions.DepotOutOfBoundsException;
import it.polimi.ingsw.Exceptions.IncompatibleResourceTypeException;
import it.polimi.ingsw.Model.Warehouse;

public interface MoveActionInterface {

    public void handleMove(Warehouse warehouse) throws IncompatibleResourceTypeException, DepotOutOfBoundsException;

}
