package it.polimi.ingsw.Model;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.Client.reducedmodel.ReducedPersonalBoard;
import it.polimi.ingsw.Commons.*;
import it.polimi.ingsw.Commons.CardType;
import it.polimi.ingsw.Commons.DevelopmentCard;
import it.polimi.ingsw.Commons.Effect;
import it.polimi.ingsw.Commons.LeaderCard;
import it.polimi.ingsw.Model.ConclusionEvents.GameEvent;
import it.polimi.ingsw.Model.ConclusionEvents.SeventhDevCardBought;
import it.polimi.ingsw.Observable;
import it.polimi.ingsw.Utils.Pair;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class PersonalBoard extends Observable<GameEvent> {
    private final Player owner;
    private final Slot[] slots;
    private ProductionRule basicProductionPower;
    private FaithTrack faithTrack;
    private final Warehouse warehouse;

    public PersonalBoard(Player owner) {
        this.owner = owner;
        this.slots = new Slot[3];
        this.slots[0] = new Slot();
        this.slots[1] = new Slot();
        this.slots[2] = new Slot();
        this.basicProductionPower = new ProductionRule();
        this.warehouse = new Warehouse();
        initializeFaithTrack();
    }

    /**
     * Gets the development card on top of the specified slot
     *
     * @param slot index of the slow where to get the card
     * @return the card on top of the slot
     */
    public DevelopmentCard peekTopCard(int slot) {
        if(this.slots[slot - 1].getNumOfStackedCards() > 0)
            return this.slots[slot - 1].peekTopCard();
        else return null;
    }

    /**
     * Calculates the victory points obtained from all the development card in the slots
     * @return the sum calculated
     */
    private int calcVictoryPointsDevCards() {
        return Arrays.stream(slots).map(Slot::getVictoryPointsSlot).reduce(0,Integer::sum);
    }

    /**
     * Calculates the victory points obtained by summing the victory points obtained from the
     * development cards, from the cells passed on the faith marker and from the resources contained
     * in the warehouse.
     * @return the sum calculated
     */
    public int calcVictoryPoints() {
        return calcVictoryPointsDevCards()+this.faithTrack.calcVictoryPoints()+this.warehouse.calcVictoryPointsWarehouse();
    }

    /**
     * Puts a development card on top of the specified slot
     * @param developmentCard the development card to put on top of the slot
     * @param slot the slot where to put the card
     */
    public void putCardOnTop(DevelopmentCard developmentCard, int slot) {
        this.slots[slot - 1].putCardOnTop(developmentCard);
        if(isSeventhCard())
            notify(new SeventhDevCardBought());
    }

    /**
     * Every time a development card is put on top of a slot, this method is called in order to check
     * if a winning condition has been reached.
     * @return if the winning condition is reached
     */
    private boolean isSeventhCard() {
        if(Arrays.stream(slots).map(Slot::getNumOfStackedCards).reduce(0,Integer::sum).equals(7) ) return true;
        else return false;
    }

    public FaithTrack getFaithTrack() {
        return faithTrack;
    }

    /**
     * Initializes the faith track parsing its standard structure from a json file contained
     * in the resources folder of the project.
     */
    private void initializeFaithTrack(){
        String path = "src/main/resources/json/faithTrack.json";

        Gson gson = new Gson();

        try{
            JsonReader reader = new JsonReader(new FileReader(path));
            this.faithTrack = gson.fromJson(reader, FaithTrack.class);
        }
        catch (FileNotFoundException e){
            // mandare messaggio al client "file di configurazione faithTrack.json non trovato"
        }
    }

    /**
     * Checks if a card of the specified level can be placed on the slot specified,
     * according to the game rules. A card of level 1 can be placed only on an empty slot.
     * A card of level 2 or 3 can be placed only on a slot where the card on top is of level = level -1.
     * @param level of the card to check
     * @param slotIndex the slot where the user wants to put the card
     * @return the check validity
     */
    public boolean isCompatibleSlot(int level, int slotIndex) {
        if(level == 1) {
            if(slots[slotIndex - 1].getNumOfStackedCards()!=0) return false;
            else return true;
        }
        else {
            if(slots[slotIndex - 1].getNumOfStackedCards() == 0) return false;
            else {
                if (slots[slotIndex - 1].peekTopCard().getType().getLevel() != level - 1)  return false;
                else return true;
            }
        }
    }

    /**
     * Checks if the requested productions specified are valid according to the personal board.
     * A player can't activate a production on an empty slot. A player can activate extra productions
     * only if he has any extra production active effect.
     * @param requestedProductions the mask containing the productions the player wants to activate
     * @return the check validity
     */
    public boolean isValidRequestedProduction(ActiveProductions requestedProductions) {
        if (requestedProductions.isSlot1()&&!(slots[0].getNumOfStackedCards()>0)) return false;
            if(requestedProductions.isSlot2()&&!(slots[1].getNumOfStackedCards()>0)) return false;
                if(requestedProductions.isSlot3()&&!(slots[2].getNumOfStackedCards()>0)) return false;
                    if(requestedProductions.isExtraSlot1()&& owner.getActiveEffects().stream().noneMatch(x -> x.getEffect() == Effect.EXTRAPRODUCTION)) return false;
                    //TODO verificare
                        if(requestedProductions.isExtraSlot2()&& !(owner.getActiveEffects().stream().filter(x -> x.getEffect() == Effect.EXTRAPRODUCTION).count() == 2)) return false;
                        return true;
    }

    /**
     * Checks if the requirements of a leader card are satisfied by checking if the requirements on resources
     * and on development cards are satisfied.
     * @param leaderCard to check
     * @return the check validity
     */
    public boolean checkLeaderRequirements(LeaderCard leaderCard){
        if(warehouse.checkResources(leaderCard.getRequirementsResources()) && checkSlotsCards(leaderCard.getRequirementsCards())){
            return true;
        }
        return false;
    }

    /**
     * Checks if the player satisfies the requirements needed to activate a leader card
     * concerning only development cards. The method gets the stream of all active cards card type
     * and the stream of requirements card type and compares them to check if the second is a
     * subset of the first. In doing this method we assumed as a game rule that leader cards which
     * require a card of level 2 can be activated only if the player has a card of that level and that color
     * in his slots.
     * @param requirements of the card as a list of couples level-color needed to activate the leader card
     * @return the check validity
     */
    private boolean checkSlotsCards(List<CardType> requirements){
        List<CardType> activeCards = new ArrayList<>();
        Arrays.stream(slots).forEach(x -> activeCards.addAll(x.getDevelopmentCardStack().stream().map(DevelopmentCard::getType).collect(Collectors.toList())));
        List<Pair<CardType, Integer>> activeCardTypes = convertToCardTypeOccurrences(activeCards);
        List<Pair<CardType, Integer>> requirementsCard = convertToCardTypeOccurrences(requirements);
        AtomicBoolean result = new AtomicBoolean(true);
        for(Pair<CardType, Integer> p : requirementsCard) {
            if(p.getKey().getLevel() > 1) {
                activeCardTypes.stream().filter(x -> x.getKey().equals(p.getKey())).findFirst().ifPresentOrElse(x -> {
                    if(x.getValue() < p.getValue()) result.set(false);
                }, ()->result.set(false));
            } else {
                List<Pair<ColorCard, Integer>> colorsRequirements = convertToColoredCardOccurrences(requirements);
                List<Pair<ColorCard, Integer>> colorsOwned = convertToColoredCardOccurrences(activeCards);

                Pair<ColorCard,Integer> pair =
                        colorsRequirements.stream().filter(x -> x.getKey().equals(p.getKey().getColor())).collect(Collectors.toList()).get(0);
                colorsOwned.stream().filter(x -> x.getKey().equals(pair.getKey())).findFirst().ifPresentOrElse(
                        x -> {
                            if(x.getValue() < pair.getValue())
                                result.set(false);
                        }, () -> result.set(false));
            }
        }
        return result.get();
    }

    /**
     * Gets a stream of card types and converts it to a stream containing, for each card type, the
     * occurrences of that card type in the original stream.
     * @param cardTypes the stream to convert
     * @return the list converted
     */
    private List<Pair<CardType, Integer>> convertToCardTypeOccurrences(List<CardType> cardTypes){
        List<Pair<CardType, Integer>> cardTypeOccurrences = new ArrayList<>();
        for(CardType cardType : cardTypes){
            if(cardTypeOccurrences.stream().anyMatch(x -> x.getKey().equals(cardType))){
                cardTypeOccurrences.stream().filter(x -> x.getKey().equals(cardType)).forEach(x -> x.setValue(x.getValue()+1));
            } else{
                cardTypeOccurrences.add(new Pair<>(cardType, 1));
            }
        }
        return cardTypeOccurrences;
    }

    /**
     * Gets a stream of card types and converts it to a stream containing, for each color present in the
     * original card types, the occurrences of that color in the original stream of card types.
     * @param cardTypes the stream to convert
     * @return the list converted
     */
    private List<Pair<ColorCard, Integer>> convertToColoredCardOccurrences(List<CardType> cardTypes) {
        List<Pair<ColorCard,Integer>> coloredCardOccurrences = new ArrayList<>();
        List<ColorCard> colors = cardTypes.stream().map(CardType::getColor).collect(Collectors.toList());
        for(ColorCard color : colors) {
            if(coloredCardOccurrences.stream().noneMatch(x -> x.getKey().equals(color))) {
                int occurrences = (int) colors.stream().filter(x -> x.equals(color)).count();
                coloredCardOccurrences.add(new Pair<>(color,occurrences));
            }
        }
        return coloredCardOccurrences;
    }


    public ReducedPersonalBoard getReducedVersion() {
        Slot[] slots = new Slot[3];
        for(int i = 0; i < slots.length; i++)  slots[i] = this.slots[i].clone();
        return new ReducedPersonalBoard(faithTrack.getReducedVersion(), warehouse.getReducedVersion(), slots, owner.getLeaderCards());
    }


    /**
     * Moves the player's faith marker on the faith track
     * @param movingPlayer the player to move
     * @param positions the number of positions to move the marker of
     */
    public void moveMarker(Player movingPlayer, int positions) {
        faithTrack.moveMarker(movingPlayer, positions);
    }



    public Warehouse getWarehouse() {
        return warehouse;
    }


}
