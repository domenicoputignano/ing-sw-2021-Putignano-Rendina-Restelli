package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.client.Checker;
import it.polimi.ingsw.client.reducedmodel.ReducedPersonalBoard;
import it.polimi.ingsw.commons.Effect;
import it.polimi.ingsw.commons.LeaderCard;
import it.polimi.ingsw.commons.LeaderEffect;
import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.model.ActiveProductions;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.ResourceSource;
import it.polimi.ingsw.utils.messages.clientMessages.ActivateProductionMessage;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This abstract class deals with main action of the game consisting in activate productions and provides some
 * methods to make consistency checks.
 */
public abstract class AbstractActivateProduction extends AbstractClientState {

    /**
     * The message to send to the server containing how to perform the action.
     */
    protected ActivateProductionMessage messageToSend = new ActivateProductionMessage();
    /**
     * The mask representing which of the available productions the client wants to active.
     */
    protected ActiveProductions requiredProduction = new ActiveProductions();

    protected AbstractActivateProduction(Client client) {
        super(client);
    }

    /**
     * Utility method used to check whether the required productions are valid.
     * @return the check validity.
     */
    public boolean areValidRequestedProductions(){
        ReducedPersonalBoard playerBoard = client.getGame().getCurrPlayer().getPersonalBoard();
        if(requiredProduction.isSlot1() && !canActivateSlot(0)) return false;
        if(requiredProduction.isSlot2() && !canActivateSlot(1)) return false;
        if(requiredProduction.isSlot3() && !canActivateSlot(2)) return false;
        if(requiredProduction.isExtraSlot1() && playerBoard.findEffect(Effect.EXTRAPRODUCTION).size() < 1) return false;
        if(requiredProduction.isExtraSlot2() && playerBoard.findEffect(Effect.EXTRAPRODUCTION).size() < 2) return false;
        return true;
    }

    /**
     * Checks whether the slot on the given index isn't empty.
     * @param index the index of the slot to check.
     * @return the check validity.
     */
    public boolean canActivateSlot(int index) {
        return !client.getGame().getCurrPlayer().getPersonalBoard().isEmptySlot(index);
    }

    /**
     * Utility method used to retrieve all the active extra production effects for the player.
     * @return the list of active extra production effects.
     */
    public List<LeaderEffect> listExtraProductionEffect() {
        return client.getGame().getCurrPlayer().getCompatibleLeaderEffect(Effect.EXTRAPRODUCTION).stream().
                map(LeaderCard::getLeaderEffect).collect(Collectors.toList());
    }

    /**
     * Gets the {@link ResourceType} associated with the extra production leader card on the given index.
     * @param leaderCardIndex the index of the leader card to retrieve.
     * @return the resource type associated with the card.
     */
    public ResourceType getExtraProductionType(int leaderCardIndex) {
        return client.getGame().getCurrPlayer().getCompatibleLeaderEffect(Effect.EXTRAPRODUCTION).
                get(leaderCardIndex).getLeaderEffect().getType();
    }

    /**
     * Computes the overall resources needed in input in order to activate the required productions.
     * @return the overall resources needed in input.
     */
    public Map<ResourceType, Integer> calculateInputResources() {
        ReducedPersonalBoard playerBoard = client.getGame().getCurrPlayer().getPersonalBoard();
        // Initialize result map
        EnumMap<ResourceType, Integer> result = new EnumMap<>(ResourceType.class);
        result.put(ResourceType.coin,0);
        result.put(ResourceType.servant,0);
        result.put(ResourceType.shield,0);
        result.put(ResourceType.stone,0);
        // Add to the result map the inputs of the basic production, if required
        if(messageToSend.getProductions().isBasic()) {
            result.put(messageToSend.getInput1(), result.get(messageToSend.getInput1())+1);
            result.put(messageToSend.getInput2(), result.get(messageToSend.getInput2())+1);
        }
        // Add to the result map the inputs of the other productions, if required
        if(messageToSend.getProductions().isSlot1()) Checker.mapMerging(retrieveCardInputResources(playerBoard,0), result);
        if(messageToSend.getProductions().isSlot2()) Checker.mapMerging(retrieveCardInputResources(playerBoard, 1), result);
        if(messageToSend.getProductions().isSlot3()) Checker.mapMerging(retrieveCardInputResources(playerBoard,2), result);
        // Add to the result map the inputs of the extra productions, if required
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

    /**
     * Utility method used to retrieve the input resources needed to activate the card on top of the given slot.
     * @param playerBoard the player's board instance.
     * @param slotIndex the index of the slot where the development card to active is located.
     * @return the input resources computed.
     */
    private Map<ResourceType, Integer> retrieveCardInputResources(ReducedPersonalBoard playerBoard, int slotIndex) {
        return playerBoard.peekTopCardInSlot(slotIndex).getTrade().getInputResources();
    }

    /**
     * Utility method called when the player has requested the extra production on the given index,
     * to find which ResourceType has to be taken in input.
     * @param playerBoard the player's board instance.
     * @param extraSlot the index of the extra production to activate.
     * @return the input {@link ResourceType} needed to activate the selected production.
     */
    private ResourceType retrieveInputExtra(ReducedPersonalBoard playerBoard, int extraSlot) {
        return playerBoard.findEffect(Effect.EXTRAPRODUCTION).get(extraSlot-1).getLeaderEffect().getType();
    }

    /**
     * Checks whether the player has enough resources to activate the requested productions.
     * @return the check validity.
     */
    public boolean checkRequiredResources(){
        Map<ResourceType, Integer> neededResources = calculateInputResources();
        return Checker.checkResources(neededResources, client.getGame().getCurrPlayer().getPersonalBoard());
    }

    /**
     * Method used to setup the message to send with the requested productions.
     * @param index the index of the slot to set.
     * @param status whether the client wants to activate the card on top of the specified index.
     */
    public void setSlot(int index, boolean status) {
        if(messageToSend.getProductions()==null) messageToSend.setProductions(requiredProduction);
        if(index == 0) requiredProduction.setSlot1(status);
        if(index == 1) requiredProduction.setSlot2(status);
        if(index == 2) requiredProduction.setSlot3(status);
    }

    /**
     * Method used to setup the message to send with the requested extra productions.
     * @param extraIndex the index of the extra production to set.
     */
    public void setExtra(int extraIndex) {
        if(messageToSend.getProductions()==null) messageToSend.setProductions(requiredProduction);
        messageToSend.setProductions(requiredProduction);
        if(extraIndex == 1) requiredProduction.setExtraSlot1(true);
        if(extraIndex == 2) requiredProduction.setExtraSlot2(true);
    }

    /**
     * Method used to setup the message to send with the basic production when the player requested it.
     */
    public void setBasicSlot() {
        if(messageToSend.getProductions()==null) messageToSend.setProductions(requiredProduction);
        requiredProduction.setBasic(true);
        messageToSend.setProductions(requiredProduction);
    }

    /**
     * Method used to setup the message to send with the input of the basic production chosen by the player.
     * @param basicInputIndex the index of the input of the basic production associated with the given resource.
     * @param resource the resource the player wants as input.
     */
    public void setInput(int basicInputIndex, ResourceType resource) {
        if(basicInputIndex == 1) messageToSend.setInput1(resource);
        if(basicInputIndex == 2) messageToSend.setInput2(resource);
    }

    /**
     * Method used to setup the message to send with the output of the productions chosen by the player.
     * @param outputType the type of production whose output has to be set.
     * @param outputIndex the index of the output to be set.
     * @param resource the {@link ResourceType} to set as output.
     */
    public void setOutput(String outputType, int outputIndex, ResourceType resource) {
        if(outputType.equalsIgnoreCase("extra")) {
            if (outputIndex == 1) messageToSend.setOutputExtra1(resource);
            if (outputIndex == 2) messageToSend.setOutputExtra2(resource);
        }
        if(outputType.equalsIgnoreCase("normal")) {
            messageToSend.setOutput(resource);
        }
    }

    /**
     * Utility method used to check whether the player has set basic production inputs and output.
     * @return the check validity.
     */
    public boolean isBasicSettingDone() {
        return messageToSend.getInput1()!=null&&messageToSend.getInput2()!=null&&messageToSend.getOutput()!=null;
    }

    /**
     * Resets the basic production inputs and output chosen.
     */
    public void clearBasicChoices() {
        messageToSend.setInput1(null);
        messageToSend.setInput2(null);
        messageToSend.setOutput(null);
    }

    /**
     * @return whether the player has required basic production.
     */
    public boolean isBasicProduction() {
        return messageToSend.getProductions().isBasic();
    }

    /**
     * Method used to setup the message to send to the server with the instructions on how to take resources needed
     * to activate the requested productions.
     * @param requiredResources the instructions on how to take resources.
     */
    public void setPaymentInstructions(Map<ResourceSource, EnumMap<ResourceType, Integer>> requiredResources) {
        messageToSend.setHowToTakeResources(requiredResources);
    }

}
