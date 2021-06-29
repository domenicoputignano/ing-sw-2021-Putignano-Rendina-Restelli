package it.polimi.ingsw.client.reducedmodel;

import it.polimi.ingsw.commons.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class represents a simplified version of a PersonalBoard with respect to the server's class.
 * It contains only the information required client side.
 */
public class ReducedPersonalBoard implements Serializable {
    /**
     * Attributes that represent the contents of the Personal Board
     */
    private final ReducedFaithTrack faithTrack;
    private final ReducedWarehouse warehouse;
    private final Slot[] slots;
    private final List<LeaderCard> leaderCards;
    /**
     * Initialize an instance by setting faithTrack,warehouse,slots and leader cards of the Personal Board.
     */
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

    /**
     * method used to check that a specific active effect is present
     * @param effect specific effect to check that it is active
     * @return
     */
    public List<LeaderCard> findEffect(Effect effect) {
        return leaderCards.stream().filter(x -> x.getLeaderEffect().getEffect().equals(effect)&&x.isActive()).collect(Collectors.toList());
    }

    /**
     * method to check that an effect associated with a specific type of resource is available
     * @param effect effect of which you want to check if it is available
     * @param type resource associated with the effect of which you want to check that it is available
     * @return true if the effect is available, false otherwise
     */
    public boolean isAvailableEffectOfType(Effect effect, ResourceType type) {
        return findEffect(effect).stream().anyMatch(x -> x.getLeaderEffect().getType() == type);
    }

    /**
     * method to check if a leader action is possible
     * @return true if a leader action is possible, false otherwise
     */
    public boolean isAvailableLeaderAction() {
        if(leaderCards.size() <= 0) return false;
        else {
            return !leaderCards.stream().allMatch(LeaderCard::isActive);
        }
    }

    /**
     * method used to check that the indicated slot is empty
     * @param slotIndex slot considered
     * @return true if the indicated slot is empty, false otherwise
     */
    public boolean isEmptySlot(int slotIndex) {
        return slots[slotIndex].getNumOfStackedCards() == 0;
    }

    /**
     * method that check if you can buy a card of the indicated level
     * @param level level considered
     * @return true if a card of the indicated level can be purchased, false otherwise
     */
    public boolean canBuyCardOfLevel(int level) {
        if(level == 1) return true;
        else {
            return (Arrays.stream(slots).anyMatch(x -> x.peekTopCard().getType().getLevel() == (level - 1)));
        }
    }

    /**
     * method that checks that a card of a specific level can be placed in a specific slot
     * @param slotIndex slot considered
     * @param level level considered
     * @return true if a card of a specific level can be placed in a specific slot, false otherwise
     */
    public boolean canPutCardInSlot(int slotIndex, int level) {
        if(slots[slotIndex-1].getNumOfStackedCards()==0) {
            return level == 1;
        } else {
            return slots[slotIndex - 1].peekTopCard().getType().getLevel() == level - 1;
        }
    }
    /**
     * Return representation of the slot top Card used by command line interface.
     */
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

    /**
     * method used to get the card on top of the considered slot
     * @param slotIndex considered slot
     * @return Development card at the top of the considered slot
     */
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
