package it.polimi.ingsw.Utils;

import it.polimi.ingsw.Exceptions.DepotNotFoundException;
import it.polimi.ingsw.Exceptions.DepotOutOfBoundsException;
import it.polimi.ingsw.Exceptions.IncompatibleResourceTypeException;
import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Model.Warehouse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveFromNormalToNormalActionTest {

    MoveFromNormalToNormalAction message;

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
        message = new MoveFromNormalToNormalAction(1, 2);
        assertTrue(message.handleMove(warehouse));
        message = new MoveFromNormalToNormalAction(1, 3);
        assertFalse(message.handleMove(warehouse));
    }

    @Test
    void isValidMove() {
        message = new MoveFromNormalToNormalAction(1, 2);
        assertTrue(message.isValidMove());
        message = new MoveFromNormalToNormalAction(4, 2);
        assertFalse(message.isValidMove());
        message = new MoveFromNormalToNormalAction(1,5);
        assertFalse(message.isValidMove());
    }
}