package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.client.Checker;
import it.polimi.ingsw.client.reducedmodel.ReducedPersonalBoard;
import it.polimi.ingsw.commons.Effect;
import it.polimi.ingsw.commons.LeaderCard;
import it.polimi.ingsw.commons.LeaderEffect;
import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.model.ActiveProductions;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.clientMessages.ActivateProductionMessage;
import it.polimi.ingsw.utils.ResourceSource;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This abstract class deals with main action of the game consisting in activate productions and provides some
 * methods to make consistency checks.
 */
public abstract class AbstractActivateProduction extends AbstractClientState {

    protected ActivateProductionMessage messageToSend = new ActivateProductionMessage();
    protected ActiveProductions requiredProduction = new ActiveProductions();
    protected Map<ResourceSource, EnumMap<ResourceType, Integer>> howToTakeResources = new HashMap<>();

    protected AbstractActivateProduction(Client client) {
        super(client);
    }

    public boolean areValidRequestedProductions(){
        ReducedPersonalBoard playerBoard = client.getGame().getCurrPlayer().getPersonalBoard();
        if(requiredProduction.isSlot1() && !canActivateSlot(0)) return false;
        if(requiredProduction.isSlot2() && !canActivateSlot(1)) return false;
        if(requiredProduction.isSlot3() && !canActivateSlot(2)) return false;
        if(requiredProduction.isExtraSlot1() && playerBoard.findEffect(Effect.EXTRAPRODUCTION).size() < 1) return false;
        if(requiredProduction.isExtraSlot2() && playerBoard.findEffect(Effect.EXTRAPRODUCTION).size() < 2) return false;
        return true;
    }

    public boolean canActivateSlot(int index) {
        return !client.getGame().getCurrPlayer().getPersonalBoard().isEmptySlot(index);
    }


    public List<LeaderEffect> listExtraProductionEffect() {
        return client.getGame().getCurrPlayer().getCompatibleLeaderEffect(Effect.EXTRAPRODUCTION).stream().
                map(LeaderCard::getLeaderEffect).collect(Collectors.toList());
    }


    public ResourceType getExtraProductionType(int leaderCardIndex) {
        return client.getGame().getCurrPlayer().getCompatibleLeaderEffect(Effect.EXTRAPRODUCTION).
                get(leaderCardIndex).getLeaderEffect().getType();
    }

    public Map<ResourceType, Integer> calculateInputResources() {
        ReducedPersonalBoard playerBoard = client.getGame().getCurrPlayer().getPersonalBoard();
        EnumMap<ResourceType, Integer> result = new EnumMap<ResourceType, Integer>(ResourceType.class);
        result.put(ResourceType.coin,0);
        result.put(ResourceType.servant,0);
        result.put(ResourceType.shield,0);
        result.put(ResourceType.stone,0);
        if(messageToSend.getProductions().isBasic()) {
            result.put(messageToSend.getInput1(), result.get(messageToSend.getInput1())+1);
            result.put(messageToSend.getInput2(), result.get(messageToSend.getInput2())+1);
        }
        if(messageToSend.getProductions().isSlot1()) Checker.mapMerging(retrieveCardInputResources(playerBoard,0), result);
        if(messageToSend.getProductions().isSlot2()) Checker.mapMerging(retrieveCardInputResources(playerBoard, 1), result);
        if(messageToSend.getProductions().isSlot3()) Checker.mapMerging(retrieveCardInputResources(playerBoard,2), result);
        if(messageToSend.getProductions().isExtraSlot1()) {
            ResourceType type = retrieveInputExtra(playerBoard, 1);
            result.put(type, result.get(type)+1);
        }
        if(messageToSend.getProductions().isExtraSlot2()) {
            ResourceType type = retrieveInputExtra(playerBoard, 2);
            result.put(type, result.get(type)+1);
        }
        return result;
    }


    private Map<ResourceType, Integer> retrieveCardInputResources(ReducedPersonalBoard playerBoard, int slotIndex) {
        return playerBoard.peekTopCardInSlot(slotIndex).getTrade().getInputResources();
    }

    //method called when we have an ExtraProduction effect, to find which ResourceType has to be transformed
    private ResourceType retrieveInputExtra(ReducedPersonalBoard playerBoard, int extraSlot) {
        return playerBoard.findEffect(Effect.EXTRAPRODUCTION).get(extraSlot-1).getLeaderEffect().getType();
    }

    public boolean checkRequiredResources(){
        Map<ResourceType, Integer> neededResources = calculateInputResources();
        return Checker.checkResources(neededResources, client.getGame().getCurrPlayer().getPersonalBoard());
    }

    public void setSlot(int index, boolean status) {
        if(messageToSend.getProductions()==null) messageToSend.setProductions(requiredProduction);
        if(index == 0) requiredProduction.setSlot1(status);
        if(index == 1) requiredProduction.setSlot2(status);
        if(index == 2) requiredProduction.setSlot3(status);
    }

    public void setExtra(int extraIndex) {
        if(messageToSend.getProductions()==null) messageToSend.setProductions(requiredProduction);
        messageToSend.setProductions(requiredProduction);
        if(extraIndex == 1) requiredProduction.setExtraSlot1(true);
        if(extraIndex == 2) requiredProduction.setExtraSlot2(true);
    }


    public void setBasicSlot() {
        if(messageToSend.getProductions()==null) messageToSend.setProductions(requiredProduction);
        requiredProduction.setBasic(true);
        messageToSend.setProductions(requiredProduction);
    }

    public void setInput(int basicInputIndex, ResourceType resource) {
        if(basicInputIndex == 1) messageToSend.setInput1(resource);
        if(basicInputIndex == 2) messageToSend.setInput2(resource);
    }
    public void setOutput(String outputType, int outputIndex, ResourceType resource) {
        if(outputType.equalsIgnoreCase("extra")) {
            if (outputIndex == 1) messageToSend.setOutputExtra1(resource);
            if (outputIndex == 2) messageToSend.setOutputExtra2(resource);
        }
        if(outputType.equalsIgnoreCase("normal")) {
            messageToSend.setOutput(resource);
        }
    }

    public boolean isBasicSettingDone() {
        return messageToSend.getInput1()!=null&&messageToSend.getInput2()!=null&&messageToSend.getOutput()!=null;
    }

    public void clearSlot() {
        requiredProduction.setDefault();
    }

    public void clearBasicChoices() {
        messageToSend.setInput1(null);
        messageToSend.setInput2(null);
        messageToSend.setOutput(null);
    }

    public boolean isBasicProduction() {
        return messageToSend.getProductions().isBasic();
    }

    public void setPaymentInstructions(Map<ResourceSource, EnumMap<ResourceType, Integer>> requiredResources) {
        messageToSend.setHowToTakeResources(requiredResources);
    }

}
