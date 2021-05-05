package it.polimi.ingsw.Client;

import it.polimi.ingsw.Commons.LeaderCard;
import it.polimi.ingsw.Commons.Slot;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ReducedPersonalBoard implements Serializable {
    private final ReducedFaithTrack faithTrack;
    private final ReducedWarehouse warehouse;
    private final Slot[] slots;
    private final List<LeaderCard> leaderCards;

    public ReducedPersonalBoard(ReducedFaithTrack faithTrack, ReducedWarehouse warehouse, Slot[] slots, List<LeaderCard> leaderCards) {
        this.faithTrack = faithTrack;
        this.warehouse = warehouse;
        this.slots = slots;
        this.leaderCards = new ArrayList<>();
        this.leaderCards.addAll(leaderCards);
    }


    public ReducedWarehouse getWarehouse() {
        return warehouse;
    }

    public Slot[] getSlots() {
        return slots;
    }
}
