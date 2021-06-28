package it.polimi.ingsw.Utils;

import it.polimi.ingsw.Model.Warehouse;

/**
 * Specific implementation of {@link MoveActionInterface} that allows the player to move resources from a {@link it.polimi.ingsw.Model.NormalDepot}
 * to another {@link it.polimi.ingsw.Model.NormalDepot}.
 */
public class MoveFromNormalToNormalAction implements MoveActionInterface {
    private int depotFrom;
    private int depotTo;

    public MoveFromNormalToNormalAction(int depotFrom, int depotTo) {
        this.depotFrom = depotFrom;
        this.depotTo = depotTo;
    }

    /**
     * Forwards move action to the warehouse calling correct method available in it.
     * @param warehouse represents where the action has to be performed.
     * @return true if the action has been correctly performed.
     */
    @Override
    public boolean handleMove(Warehouse warehouse) {
        if(warehouse.checkMoveFromNormalDepotToNormalDepot(depotFrom, depotTo)){
            warehouse.moveFromNormalDepotToNormalDepot(depotFrom, depotTo);
            return true;
        }
        return false;
    }

    /**
     * Checks whether the move is correct performing indexes inspection.
     * @return the result of the check
     */
    @Override
    public boolean isValidMove() {
        if(depotFrom<=0 || depotFrom>3) return false;
        if(depotTo<=0 || depotTo>3) return false;
        return true;
    }

}
