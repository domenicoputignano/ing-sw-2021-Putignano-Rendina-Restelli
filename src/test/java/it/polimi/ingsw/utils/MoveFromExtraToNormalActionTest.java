package it.polimi.ingsw.utils;

import it.polimi.ingsw.exceptions.DepotNotFoundException;
import it.polimi.ingsw.exceptions.DepotOutOfBoundsException;
import it.polimi.ingsw.exceptions.IncompatibleResourceTypeException;
import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.model.Warehouse;
import it.polimi.ingsw.utils.messages.clientMessages.ActivateProductionMessage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class whose task is to check all possible outcomes for a move message requiring a move between a {@link it.polimi.ingsw.model.ExtraDepot}
 * and a {@link it.polimi.ingsw.model.NormalDepot}.
 */
class MoveFromExtraToNormalActionTest {

    MoveFromExtraToNormalAction message;


    /**
     * Checks move execution and its outcome.
     */
    @Test
    void handleMove() throws DepotOutOfBoundsException, IncompatibleResourceTypeException, DepotNotFoundException {
        Warehouse warehouse = new Warehouse();
        warehouse.addResourcesToDepot(1, ResourceType.shield, 1);
        warehouse.addResourcesToDepot(2, ResourceType.servant, 0);
        warehouse.addResourcesToDepot(3, ResourceType.stone, 2);
        warehouse.initializeExtraDepot(ResourceType.servant);
        warehouse.initializeExtraDepot(ResourceType.stone);
        warehouse.addResourcesToExtraDepot(ResourceType.servant, 2);
        warehouse.addResourcesToExtraDepot(ResourceType.stone, 1);
        message = new MoveFromExtraToNormalAction(2, 1, 3);
        assertTrue(message.handleMove(warehouse));
        message = new MoveFromExtraToNormalAction(2, 3, 3);
        assertFalse(message.handleMove(warehouse));
    }

    /**
     * Checks move validity.
     */
    @Test
    void isValidMove() {
        message = new MoveFromExtraToNormalAction(1, 2, 1);
        assertTrue(message.isValidMove());
        message = new MoveFromExtraToNormalAction(3, 2, 1);
        assertFalse(message.isValidMove());
        message = new MoveFromExtraToNormalAction(1, 90, 2);
        assertFalse(message.isValidMove());
        message = new MoveFromExtraToNormalAction(1, -2, 1);
        assertFalse(message.isValidMove());
    }
}