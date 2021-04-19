package it.polimi.ingsw.Utils;

import it.polimi.ingsw.Model.Warehouse;

public class MoveFromNormalToNormalAction implements MoveActionInterface {
    private int depotFrom;
    private int depotTo;

    public MoveFromNormalToNormalAction(int depotFrom, int depotTo) {
        this.depotFrom = depotFrom;
        this.depotTo = depotTo;
    }

    @Override
    public boolean handleMove(Warehouse warehouse) {
        if(warehouse.checkMoveFromNormalDepotToNormalDepot(depotFrom, depotTo)){
            warehouse.moveFromNormalDepotToNormalDepot(depotFrom, depotTo);
            return true;
        }
        return false;
    }

}
