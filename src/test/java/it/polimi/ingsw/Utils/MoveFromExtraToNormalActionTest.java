package it.polimi.ingsw.Utils;

import it.polimi.ingsw.Exceptions.DepotNotFoundException;
import it.polimi.ingsw.Exceptions.DepotOutOfBoundsException;
import it.polimi.ingsw.Exceptions.IncompatibleResourceTypeException;
import it.polimi.ingsw.Model.ExtraDepot;
import it.polimi.ingsw.Model.NormalDepot;
import it.polimi.ingsw.Model.ResourceType;
import it.polimi.ingsw.Model.Warehouse;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoveFromExtraToNormalActionTest {

    MoveFromExtraToNormalAction message;

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