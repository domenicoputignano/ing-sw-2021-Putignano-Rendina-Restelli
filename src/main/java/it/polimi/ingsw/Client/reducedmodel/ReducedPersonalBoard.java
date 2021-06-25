package it.polimi.ingsw.Client.reducedmodel;

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

    public int getNumOfNotActiveLeaderCards() { return leaderCards.size()-(int)leaderCards.stream().filter(LeaderCard::isActive).count(); }

    public int getNumOfLeaderCards() { return leaderCards.size(); }

    public Slot[] getSlots() {
        return slots;
    }

    public List<LeaderCard> getAvailableLeaderCards() {
        return leaderCards;
    }

    public List<LeaderCard> findEffect(Effect effect) {
        return leaderCards.stream().filter(x -> x.getLeaderEffect().getEffect().equals(effect)&&x.isActive()).collect(Collectors.toList());
    }

    public boolean isAvailableEffectOfType(Effect effect, ResourceType type) {
        return findEffect(effect).stream().anyMatch(x -> x.getLeaderEffect().getType() == type);
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


    public boolean canBuyCardOfLevel(int level) {
        if(level == 1) return true;
        else {
            return (Arrays.stream(slots).anyMatch(x -> x.peekTopCard().getType().getLevel() == (level - 1)));
        }
    }

    public boolean canPutCardInSlot(int slotIndex, int level) {
        if(slots[slotIndex-1].getNumOfStackedCards()==0) {
            return level == 1;
        } else {
            return slots[slotIndex - 1].peekTopCard().getType().getLevel() == level - 1;
        }
    }

    public String getSlotTopCardAsASCII(int slotIndex, int row) {
        if(!isEmptySlot(slotIndex)) {
            DevelopmentCard card = slots[slotIndex].peekTopCard();
            return card.toASCII(row);
        }
        else
            return "                           ";

    }

    public Slot getSlot(int index) {
        return slots[index];
    }

    public DevelopmentCard peekTopCardInSlot(int slotIndex) {
        return slots[slotIndex].peekTopCard();
    }

    public ReducedFaithTrack getFaithTrack() {
        return faithTrack;
    }

    public List<CardType> getDevelopmentCardsInfo() {
        List<DevelopmentCard> cards = new ArrayList<>();
        Arrays.stream(slots).forEach(x -> cards.addAll(x.getDevelopmentCardStack()));
        return cards.stream().map(DevelopmentCard::getType).collect(Collectors.toList());
    }

}
