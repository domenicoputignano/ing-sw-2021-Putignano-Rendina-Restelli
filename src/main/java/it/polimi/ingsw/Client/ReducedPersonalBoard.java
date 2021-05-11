package it.polimi.ingsw.Client;

import it.polimi.ingsw.Commons.Effect;
import it.polimi.ingsw.Commons.LeaderCard;
import it.polimi.ingsw.Commons.Slot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


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

    public List<LeaderCard> findEffect(Effect effect) {
        return leaderCards.stream().filter(x -> x.getLeaderEffect().getEffect().equals(effect)&&x.isActive()).collect(Collectors.toList());
    }




}
