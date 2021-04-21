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

    @Override
    public boolean isValidMove() {
        if(extraDepotFrom<=0 || extraDepotFrom>2) return false;
        if(occ<0 || occ>2) return false;
        if(depotTo<=0 || depotTo>3) return false;
        return true;
    }
}
