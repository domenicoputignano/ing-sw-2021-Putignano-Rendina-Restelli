package it.polimi.ingsw.Utils;

import it.polimi.ingsw.Model.Warehouse;

public class MoveFromExtraToNormalAction implements MoveActionInterface {
    private int extraDepotFrom;
    private int occ;
    private int depotTo;

    public MoveFromExtraToNormalAction(int extraDepotFrom, int occ, int depotTo) {
        this.extraDepotFrom = extraDepotFrom;
        this.occ = occ;
        this.depotTo = depotTo;
    }

    public boolean handleMove(Warehouse warehouse) {
        if(warehouse.checkMoveFromExtraDepotToNormalDepot(extraDepotFrom, occ, depotTo)) {
            warehouse.moveFromExtraDepotToNormalDepot(extraDepotFrom, occ, depotTo);
            return true;
        }
        return false;
    }
}
