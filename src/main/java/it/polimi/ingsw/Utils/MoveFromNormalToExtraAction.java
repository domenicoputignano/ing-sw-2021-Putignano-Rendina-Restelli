package it.polimi.ingsw.Utils;

import it.polimi.ingsw.Exceptions.DepotOutOfBoundsException;
import it.polimi.ingsw.Model.Warehouse;

public class MoveFromNormalToExtraAction implements MoveActionInterface {
    private int depotFrom;
    private int occ;
    private int extraDepotTo;


    public MoveFromNormalToExtraAction(int depotFrom, int occ, int extraDepotTo) {
        this.depotFrom = depotFrom;
        this.occ = occ;
        this.extraDepotTo = extraDepotTo;
    }

    public void handleMove(Warehouse warehouse) throws DepotOutOfBoundsException {
        if(warehouse.checkMoveFromNormalDepotToExtraDepot(depotFrom, occ, extraDepotTo)) {
            warehouse.moveFromNormalDepotToExtraDepot(depotFrom, occ, extraDepotTo);
        }
    }


}
