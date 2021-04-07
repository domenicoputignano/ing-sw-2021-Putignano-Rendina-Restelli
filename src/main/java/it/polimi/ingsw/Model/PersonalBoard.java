package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Card.*;

import java.util.Stack;

public class PersonalBoard {
    private Stack<DevelopmentCard>[] slots;
    private ProductionRule basicProductionPower;
    private FaithTrack faithTrack;

    public Stack<DevelopmentCard>[] getSlots() {
        return slots;
    }

    public ProductionRule getBasicProductionPower() {
        return basicProductionPower;
    }

    public FaithTrack getFaithTrack() {
        return faithTrack;
    }
}
