package it.polimi.ingsw.Model;

import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Commons.Effect;
import it.polimi.ingsw.Utils.Messages.ClientMessages.*;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.ActivateProductionUpdate;

import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


/**
 * This class represents activate production action with all the method necessary to perform it.
 */
public class ActivateProduction implements AbstractTurnPhase {
    private final Logger LOGGER = Logger.getLogger(ActivateProduction.class.getName());
    private ActivateProductionMessage activateProductionMessage;

    /**
     * Resources that player pays to perform productions.
     */
    private final Map<ResourceType, Integer> inputResources;

    /**
     * Resources that player gets from productions.
     */
    private final Map<ResourceType, Integer> outputResources;

    /**
     * Number of faith points gained from productions.
     */
    private int faith = 0;


    /**
     * Constructor sets up properly maps.
     */
    public ActivateProduction() {
        inputResources = new EnumMap<>(ResourceType.class);
        inputResources.put(ResourceType.coin, 0);
        inputResources.put(ResourceType.servant, 0);
        inputResources.put(ResourceType.shield, 0);
        inputResources.put(ResourceType.stone, 0);
        outputResources = new EnumMap<>(inputResources);
    }


    /**
     * Performs an activate production action. In the first place, it checks if the player has enough
     * resources to do the action, then it takes resources from player warehouse and it puts resulting resources
     * in the strongbox.
     * It doesn't expect any exception like: {@link DepotOutOfBoundsException}, {@link DepotNotFoundException} or {@link StrongboxOutOfBoundException}
     * because the action requires several checks done before retrieving resources from warehouse.
     * @param turn turn in which the action has to be performed.
     * @param activateProductionMessage message containing instruction on how to do the action.
     */

    public void activateProduction(Turn turn, ActivateProductionMessage activateProductionMessage) throws InvalidActionException, PaymentErrorException, NotEnoughResourcesException, ResourceMismatchException {
        if(turn.isDoneNormalAction())
            throw new InvalidActionException();
        this.activateProductionMessage = activateProductionMessage;
        calculateResources(turn, activateProductionMessage.getProductions());
        if(PaymentHandler.checkCostCoherence(activateProductionMessage.getHowToTakeResources(),inputResources)) {
            if (turn.getPlayer().getPersonalBoard().getWarehouse().checkResources(inputResources)) {
                Warehouse playerWarehouse = turn.getPlayer().getPersonalBoard().getWarehouse();
                try {
                    PaymentHandler.performPayment(playerWarehouse, activateProductionMessage.getHowToTakeResources(), turn);
                    playerWarehouse.addResourcesToStrongbox(outputResources);
                    turn.getPlayer().getPersonalBoard().moveMarker(turn.getPlayer(),faith);
                    turn.normalActionDone();
                    turn.getGame().notifyUpdate(new ActivateProductionUpdate(turn.getPlayer().getUser(), turn.getPlayer().getReducedPersonalBoard(),
                            inputResources, outputResources, faith));
                }
                catch (DepotOutOfBoundsException | DepotNotFoundException | StrongboxOutOfBoundException e) {
                    LOGGER.log(Level.SEVERE, "Critical error detected: exception not expected thrown!");
                }
            } else  throw new NotEnoughResourcesException();
        } else  throw new ResourceMismatchException();

    }


    /**
     * Calculate the cost of all required productions.
     * @param requestedProductions kind of productions required by player.
     */
    private void calculateResources(Turn turn, ActiveProductions requestedProductions) {
        PersonalBoard personalBoard = turn.getPlayer().getPersonalBoard();

        if(requestedProductions.isSlot1()) {
            personalBoard.peekTopCard(1).getTrade().getInputResources().forEach((key, value) -> inputResources.merge(key,value,Integer::sum));
            personalBoard.peekTopCard(1).getTrade().getOutputResources().forEach((key, value) -> outputResources.merge(key,value,Integer::sum));
            faith += personalBoard.peekTopCard(1).getTrade().getOutputFaith();
        }
        if(requestedProductions.isSlot2()) {
            personalBoard.peekTopCard(2).getTrade().getInputResources().forEach((key, value) -> inputResources.merge(key,value,Integer::sum));
            personalBoard.peekTopCard(2).getTrade().getOutputResources().forEach((key, value) -> outputResources.merge(key,value,Integer::sum));
            faith += personalBoard.peekTopCard(2).getTrade().getOutputFaith();
        }
        if(requestedProductions.isSlot3()) {
            personalBoard.peekTopCard(3).getTrade().getInputResources().forEach((key, value) -> inputResources.merge(key,value,Integer::sum));
            personalBoard.peekTopCard(3).getTrade().getOutputResources().forEach((key, value) -> outputResources.merge(key,value,Integer::sum));
            faith += personalBoard.peekTopCard(3).getTrade().getOutputFaith();
        }
        if(requestedProductions.isBasic()) {
            inputResources.put(activateProductionMessage.getInput1(), inputResources.get(activateProductionMessage.getInput1())+1);
            inputResources.put(activateProductionMessage.getInput2(), inputResources.get(activateProductionMessage.getInput2())+1);
            outputResources.put(activateProductionMessage.getOutput(), outputResources.get(activateProductionMessage.getOutput())+1);
        }
        if(requestedProductions.isExtraSlot1()|| requestedProductions.isExtraSlot2())
            performExtraProductionEffect(turn,requestedProductions);
    }


    /**
     * Adds to input and output resources occurrences of resources coming from an extra production effect
     */
    private void performExtraProductionEffect(Turn turn, ActiveProductions requestedProductions) {
        if(requestedProductions.isExtraSlot1()) {
            ResourceType extraType1 = turn.getPlayer().getActiveEffects().stream().filter(x -> x.getEffect() == Effect.EXTRAPRODUCTION).collect(Collectors.toList()).
                    get(0).getType();
            ResourceType extraOutput1 = activateProductionMessage.getOutputExtra1();
            inputResources.put(extraType1, inputResources.get(extraType1)+1);
            outputResources.put(extraOutput1, outputResources.get(extraOutput1)+1);
            faith++;
        }
        if(requestedProductions.isExtraSlot2()) {
            ResourceType extraType2 = turn.getPlayer().getActiveEffects().stream().filter(x -> x.getEffect() == Effect.EXTRAPRODUCTION).collect(Collectors.toList()).
                    get(1).getType();
            ResourceType extraOutput2 = activateProductionMessage.getOutputExtra2();
            inputResources.put(extraType2, inputResources.get(extraType2)+1);
            outputResources.put(extraOutput2, outputResources.get(extraOutput2)+1);
            faith++;
        }
    }


    public Map<ResourceType, Integer> getInputResources() {
        return inputResources;
    }

    public Map<ResourceType, Integer> getOutputResources() {
        return outputResources;
    }
}
