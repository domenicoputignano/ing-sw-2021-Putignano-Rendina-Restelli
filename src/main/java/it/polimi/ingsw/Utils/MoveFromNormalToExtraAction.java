package it.polimi.ingsw.Utils;

import it.polimi.ingsw.Model.Warehouse;

/**
 * A specific implementation of {@link MoveActionInterface} that allows the player to move some resources from
 * a {@link it.polimi.ingsw.Model.NormalDepot} to an {@link it.polimi.ingsw.Model.ExtraDepot} given their indexes.
 */
public class MoveFromNormalToExtraAction implements MoveActionInterface {
    private int depotFrom;
    private int occ;
    private int extraDepotTo;


    /**
     * Initializes move action.
     * @param depotFrom index of starting normal depot.
     * @param occ number of resources to be moved.
     * @param extraDepotTo index of extra depot destination.
     */
    public MoveFromNormalToExtraAction(int depotFrom, int occ, int extraDepotTo) {
        this.depotFrom = depotFrom;
        this.occ = occ;
        this.extraDepotTo = extraDepotTo;
    }

    /**
     * Forwards move action to the warehouse calling correct method available in it.
     * @param warehouse element of player board where resources can be placed and moved.
     * @return true if the action has been successfully performed.
     */
    public boolean handleMove(Warehouse warehouse) {
        if(warehouse.checkMoveFromNormalDepotToExtraDepot(depotFrom, occ, extraDepotTo)) {
            warehouse.moveFromNormalDepotToExtraDepot(depotFrom, occ, extraDepotTo);
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
        if(occ<0 || occ>2) return false;
        if(extraDepotTo<=0 || extraDepotTo>2) return false;
        return true;
    }

}
