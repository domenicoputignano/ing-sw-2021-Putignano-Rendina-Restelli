package it.polimi.ingsw.Model;

import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Exceptions.DepotOutOfBoundsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class NormalDepotTest {
    int size= 3;
    NormalDepot depot = new NormalDepot(size);
    NormalDepot depot1 = new NormalDepot(2, ResourceType.coin,size);

    @Test
    void initialize() {
        assertTrue(depot.getOcc()==0);
        assertNull(depot.getType());
        assertTrue(depot.getSize()==size);
        assertTrue(depot1.getOcc()==2);
        assertTrue(depot1.getType()==ResourceType.coin);
        assertTrue(depot1.getSize()==size);
    }

    @Test
    void add() throws DepotOutOfBoundsException {
        NormalDepot depotClone = new NormalDepot(depot.getOcc(),depot.getType(),depot.getSize());
        depot.add(1);
        assertEquals(depotClone.getOcc()+1,depot.getOcc());
    }

    @Test
    void addException() throws DepotOutOfBoundsException {
        NormalDepot depotClone = new NormalDepot(depot.getOcc(),depot.getType(),depot.getSize());
        assertThrows(DepotOutOfBoundsException.class,()->depot.add(4));
    }

    @ParameterizedTest
    @ValueSource(ints = {0,1,2,3})
    void parametrizedtake(int size) throws DepotOutOfBoundsException {
        Random r = new Random();
        int toadd = r.nextInt(size+1);
        int totake = r.nextInt(size+1);
        NormalDepot depot = new NormalDepot(0,ResourceType.servant,size);
        depot.add(toadd);
        NormalDepot depotclone = new NormalDepot(depot.getOcc(), depot.getType(), depot.getSize());
        if(toadd>=totake) {
            depot.take(totake);
            assertEquals(depotclone.getOcc()-totake, depot.getOcc());
        }
    }

    @Test
    void take() throws DepotOutOfBoundsException {
        depot.add(2);
        NormalDepot depotClone = new NormalDepot(depot.getOcc(),depot.getType(),depot.getSize());
        depot.take(1);
        assertEquals(depotClone.getOcc()-1,depot.getOcc());
    }

    @Test
    void takeException() throws DepotOutOfBoundsException {
        depot.add(2);
        NormalDepot depotClone = new NormalDepot(depot.getOcc(),depot.getType(),depot.getSize());
        assertThrows(DepotOutOfBoundsException.class,()->depot.take(3));
    }

    @Test
    void setType() {
        depot.setType(ResourceType.coin);
        assertTrue(depot.getType()==ResourceType.coin);
    }
}