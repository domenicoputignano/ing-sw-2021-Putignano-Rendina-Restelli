package it.polimi.ingsw.Model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.Model.Card.*;
import it.polimi.ingsw.Utils.Pair;

import javax.smartcardio.Card;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class PersonalBoard {
    private Player owner;
    private Stack<DevelopmentCard>[] slots;
    private ProductionRule basicProductionPower;
    private FaithTrack faithTrack;
    private Warehouse warehouse;

    public PersonalBoard(Player owner) {
        this.owner = owner;
        this.slots = new Stack[3];
        this.slots[0] = new Stack<DevelopmentCard>();
        this.slots[1] = new Stack<DevelopmentCard>();
        this.slots[2] = new Stack<DevelopmentCard>();
        this.basicProductionPower = new ProductionRule();
        this.warehouse = new Warehouse();
        initializeFaithTrack();
    }

    public Stack<DevelopmentCard>[] getSlots() {
        return slots;
    }

    public ProductionRule getBasicProductionPower() {
        return basicProductionPower;
    }

    public FaithTrack getFaithTrack() {
        return faithTrack;
    }

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



    public boolean isCompatibleSlot(int level, int slotIndex) {
        if(level == 1) {
            if(slots[slotIndex - 1].size()!=0) return false;
            else return true;
        }
        else {
            if(slots[slotIndex - 1].size() == 0) return false;
            else {
                if (slots[slotIndex - 1].peek().getType().getLevel() != level - 1)  return false;
                else return true;
            }
        }
    }

    public boolean isValidRequestedProduction(ActiveProductions requestedProductions) {
        if (requestedProductions.isSlot1()&&!(slots[0].size()>0)) return false;
            if(requestedProductions.isSlot2()&&!(slots[1].size()>0)) return false;
                if(requestedProductions.isSlot3()&&!(slots[2].size()>0)) return false;
                    if(requestedProductions.isExtraSlot1()&& !(owner.getActiveEffects().stream().filter(x -> x.getEffect() == Effect.EXTRAPRODUCTION).count() > 0)) return false;
                        if(requestedProductions.isExtraSlot1()&& !(owner.getActiveEffects().stream().filter(x -> x.getEffect() == Effect.EXTRAPRODUCTION).count() == 2)) return false;
                        return true;
    }

    public boolean checkLeaderRequirements(LeaderCard leaderCard){
        if(warehouse.checkResources(leaderCard.getRequirementsResources()) && checkSlotsCards(leaderCard.getRequirementsCards())){
            return true;
        }
        return false;
    }

    private boolean checkSlotsCards(List<CardType> requirements){
        List<CardType> activeCards = new ArrayList<>();
        Arrays.asList(slots).stream().forEach(x -> activeCards.addAll(x.stream().map(DevelopmentCard::getType).collect(Collectors.toList())));
        List<Pair<CardType, Integer>> activeCardTypes = convertToCardTypeOccurrences(activeCards);
        List<Pair<CardType, Integer>> requirementsCard = convertToCardTypeOccurrences(requirements);
        for(Pair<CardType, Integer> p : requirementsCard){
            if(activeCardTypes.stream().anyMatch(x -> x.getKey().equals(p.getKey()))){
                if(activeCardTypes.stream().filter(x -> x.getKey().equals(p.getKey())).findFirst().get().getValue() < p.getValue()) return false;
            } else return false;
        }
        return true;
    }

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

    public Warehouse getWarehouse() {
        return warehouse;
    }

}
