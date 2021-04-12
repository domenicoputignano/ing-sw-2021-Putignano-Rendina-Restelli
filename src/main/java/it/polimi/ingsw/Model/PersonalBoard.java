package it.polimi.ingsw.Model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.Model.Card.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PersonalBoard {
    private Stack<DevelopmentCard>[] slots;
    private ProductionRule basicProductionPower;
    private FaithTrack faithTrack;
    private Warehouse warehouse;

    public PersonalBoard() {
        this.slots = new Stack[3];
        this.slots[0] = new Stack<DevelopmentCard>();
        this.slots[1] = new Stack<DevelopmentCard>();
        this.slots[2] = new Stack<DevelopmentCard>();
        this.basicProductionPower = null;
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

    public Warehouse getWarehouse() {
        return warehouse;
    }
}
