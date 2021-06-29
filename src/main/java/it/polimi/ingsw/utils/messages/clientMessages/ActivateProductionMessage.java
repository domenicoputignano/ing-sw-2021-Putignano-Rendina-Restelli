package it.polimi.ingsw.utils.messages.clientMessages;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.model.ActiveProductions;
import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.network.RemoteView;
import it.polimi.ingsw.utils.ResourceSource;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import static it.polimi.ingsw.model.PaymentHandler.areValidInstructions;

/**
 * This class contains information regarding an {@link it.polimi.ingsw.model.ActivateProduction} action.
 */
public class ActivateProductionMessage implements TurnControllerHandleable {
    /**
     * Chosen productions.
     */
    private ActiveProductions productions;
    /**
     * Resource type as input of basic production.
     */
    private ResourceType input1;
    /**
     * Resource type as input of basic production.
     */
    private ResourceType input2;
    /**
     * Resource type as output of basic production.
     */
    private ResourceType output;
    /**
     * Resource type as outcome of the first possible extra production.
     */
    private ResourceType outputExtra1;
    /**
     * Resource type as outcome of the second possible extra production.
     */
    private ResourceType outputExtra2;
    /**
     * Map containing instruction on how to get resources from player's warehouse.
     */
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

    /**
     * Boolean that checks low level correctness of the message, in particular way it ensures that at least
     * one production has been selected and then that input/output have been chosen for those who required them
     * to be performed.
     */
    public boolean isValidMessage()
    {
        if(productions == null) return false;
        if(productions.noneSlotSelected()) return false;
        if(productions.isBasic() && (input1 == null || input2 == null || output == null)) return false;
        if(productions.isExtraSlot1() && (outputExtra1 == null)) return false;
        if(productions.isExtraSlot2() && (outputExtra2 == null)) return false;
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


    /**
     * Calls right method of turn controller in order to process the message itself and perform the action required by player.
     * @param turnController turn controller instance that process the message.
     * @param sender remote view that forwards the message.
     */
    @Override
    public void handleMessage(TurnController turnController, RemoteView sender) {
        turnController.handleActivateProductionMessage(this,sender);
    }

    /**
     * This method is called in order to forward message itself to turn controller.
     * @param gameController game controller instance that will process the message.
     * @param sender remote view that forwards the message.
     */
    @Override
    public void handleMessage(GameController gameController, RemoteView sender) {
        handleMessage(gameController.getTurnController(), sender);
    }
}
