package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.Warehouse;

/**
 * A specific implementation of move action that involves an extra depot as starting point and a normal one as destination.
 */
public class MoveFromExtraToNormalAction implements MoveActionInterface {
    private int extraDepotFrom;
    private int occ;
    private int depotTo;

    /**
     * Creates an instance of the class initializing index of the depots.
     */
    public MoveFromExtraToNormalAction(int extraDepotFrom, int occ, int depotTo) {
        this.extraDepotFrom = extraDepotFrom;
        this.occ = occ;
        this.depotTo = depotTo;
    }

    /**
     * Forwards move action to the warehouse calling correct method available in it.
     * @param warehouse element of player board where resources can be placed and moved.
     * @return true if the action has been successfully performed.
     */
    @Override
    public boolean handleMove(Warehouse warehouse) {
        if(warehouse.checkMoveFromExtraDepotToNormalDepot(extraDepotFrom, occ, depotTo)) {
            warehouse.moveFromExtraDepotToNormalDepot(extraDepotFrom, occ, depotTo);
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
        if(extraDepotFrom<=0 || extraDepotFrom>2) return false;
        if(occ<0 || occ>2) return false;
        if(depotTo<=0 || depotTo>3) return false;
        return true;
    }
}
