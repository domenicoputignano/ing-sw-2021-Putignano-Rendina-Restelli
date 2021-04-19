package it.polimi.ingsw.Utils;

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

    public boolean handleMove(Warehouse warehouse) {
        if(warehouse.checkMoveFromNormalDepotToExtraDepot(depotFrom, occ, extraDepotTo)) {
            warehouse.moveFromNormalDepotToExtraDepot(depotFrom, occ, extraDepotTo);
            return true;
        }
        return false;
    }


}
