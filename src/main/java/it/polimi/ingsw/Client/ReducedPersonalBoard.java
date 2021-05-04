package it.polimi.ingsw.Client;

import it.polimi.ingsw.Commons.Slot;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;


public class ReducedPersonalBoard implements Serializable {
    private final ReducedFaithTrack faithTrack;
    private final ReducedWarehouse warehouse;
    private final Slot[] slots;

    public ReducedPersonalBoard(ReducedFaithTrack faithTrack, ReducedWarehouse warehouse, Slot[] slots) {
        this.faithTrack = faithTrack;
        this.warehouse = warehouse;
        this.slots = slots;
    }


    public ReducedWarehouse getWarehouse() {
        return warehouse;
    }

    public Slot[] getSlots() {
        return slots;
    }
}
