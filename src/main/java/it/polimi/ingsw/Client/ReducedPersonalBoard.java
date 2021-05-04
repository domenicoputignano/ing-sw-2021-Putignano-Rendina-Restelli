package it.polimi.ingsw.Client;

import it.polimi.ingsw.Commons.DevelopmentCard;

import java.io.Serializable;
import java.util.Stack;

public class ReducedPersonalBoard implements Serializable {
    private final ReducedFaithTrack faithTrack;
    private final ReducedWarehouse warehouse;
    private final Stack<DevelopmentCard>[] slots;

    public ReducedPersonalBoard(ReducedFaithTrack faithTrack, ReducedWarehouse warehouse, Stack<DevelopmentCard>[] slots) {
        this.faithTrack = faithTrack;
        this.warehouse = warehouse;
        this.slots = slots;
    }
}
