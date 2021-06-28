package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.Warehouse;

import java.io.Serializable;

/**
 * This interface is part of the move action message and allows to the player to choose between different ways of moving
 * resources in his warehouse.
 */
public interface MoveActionInterface extends Serializable {

    /**
     * Forwards move action to the warehouse
     * @param warehouse represents where the action has to be performed.
     * @return true if the action successfully ended, false if it cannot be performed
     **/
    boolean handleMove(Warehouse warehouse);

    /**
     * Check whether the action is compliant with game rules, depending on which kind of move has been selected.
     * @return the result of the check.
     */
    boolean isValidMove();
}
