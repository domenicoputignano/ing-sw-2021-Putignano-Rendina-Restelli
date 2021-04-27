package it.polimi.ingsw.Utils.Messages;

import it.polimi.ingsw.Model.ActiveProductions;
import it.polimi.ingsw.Model.ResourceType;
import it.polimi.ingsw.Utils.Messages.ClientMessage;
import it.polimi.ingsw.Utils.ResourceSource;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import static it.polimi.ingsw.Model.PaymentHandler.areValidInstructions;

public class ActivateProductionMessage {
    private ActiveProductions productions;
    private ResourceType input1;
    private ResourceType input2;
    private ResourceType output;
    private ResourceType outputExtra1;
    private ResourceType outputExtra2;
    private Map<ResourceSource, EnumMap<ResourceType,Integer>> howToTakeResources = new HashMap<>();

    public ActiveProductions getProductions() {
        return productions;
    }

    public ResourceType getInput1() {
        return input1;
    }

    public ResourceType getInput2() {
        return input2;
    }

    public ResourceType getOutput() {
        return output;
    }

    public ResourceType getOutputExtra1() {
        return outputExtra1;
    }

    public ResourceType getOutputExtra2() {
        return outputExtra2;
    }

    public Map<ResourceSource, EnumMap<ResourceType, Integer>> getHowToTakeResources() {
        return howToTakeResources;
    }

    public boolean isValidMessage()
    {
        if(productions == null) return false;
        if(productions.noneSlotSelected()) return false;
        if(productions.isBasic() && (input1 == null || input2 == null || output == null)) return false;
        if(productions.isExtraSlot1() && (outputExtra1 == null)) return false;
        if(productions.isExtraSlot2() && (outputExtra2 == null)) return false;
        // TODO : fare metodo per evitare le duplicazioni
        return areValidInstructions(howToTakeResources);
    }



    public void setProductions(ActiveProductions productions) {
        this.productions = productions;
    }

    public void setInput1(ResourceType input1) {
        this.input1 = input1;
    }

    public void setInput2(ResourceType input2) {
        this.input2 = input2;
    }

    public void setOutput(ResourceType output) {
        this.output = output;
    }

    public void setOutputExtra1(ResourceType outputExtra1) {
        this.outputExtra1 = outputExtra1;
    }

    public void setOutputExtra2(ResourceType outputExtra2) {
        this.outputExtra2 = outputExtra2;
    }

    public void setHowToTakeResources(Map<ResourceSource, EnumMap<ResourceType, Integer>> howToTakeResources) {
        this.howToTakeResources = howToTakeResources;
    }
}
