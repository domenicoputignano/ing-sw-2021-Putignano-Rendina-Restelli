package it.polimi.ingsw.Client;

import it.polimi.ingsw.Commons.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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

    public Map<ResourceType, Integer> getAvailableResources() {
        return warehouse.getAvailableResources();
    }

    public int getNumOfAvailableLeaderCards() { return (int)leaderCards.stream().filter(LeaderCard::isActive).count(); }

    public Slot[] getSlots() {
        return slots;
    }

    public List<LeaderCard> findEffect(Effect effect) {
        return leaderCards.stream().filter(x -> x.getLeaderEffect().getEffect().equals(effect)&&x.isActive()).collect(Collectors.toList());
    }

    public boolean isAvailableLeaderAction() {
        if(leaderCards.size() <= 0) return false;
        else {
            return !leaderCards.stream().allMatch(LeaderCard::isActive);
        }
    }

    public boolean isEmptySlot(int slotIndex) {
        return slots[slotIndex].getNumOfStackedCards() == 0;
    }

    public DevelopmentCard peekTopCardInSlot(int slotIndex) {
        return slots[slotIndex].peekTopCard();
    }

    public boolean canBuyCardOfLevel(int level) {
        return level == 1 || Arrays.stream(slots).anyMatch(x -> x.peekTopCard().getType().getLevel() == (level - 1));
    }

    public Slot getSlot(int index) {
        return slots[index];
    }

}
