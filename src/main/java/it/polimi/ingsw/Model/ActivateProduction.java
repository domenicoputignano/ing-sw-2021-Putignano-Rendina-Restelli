package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.DepotNotFoundException;
import it.polimi.ingsw.Exceptions.DepotOutOfBoundsException;
import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Exceptions.StrongboxOutOfBoundException;
import it.polimi.ingsw.Model.Card.Effect;
import it.polimi.ingsw.Utils.Messages.ActivateProductionMessage;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ActivateProduction implements AbstractTurnPhase {

    private ActivateProductionMessage activateProductionMessage;
    private Map<ResourceType, Integer> inputResources = new EnumMap<ResourceType, Integer>(ResourceType.class);
    private Map<ResourceType, Integer> outputResources = new EnumMap<ResourceType, Integer>(ResourceType.class);
    private int faith = 0;

    public void activateProduction(Turn turn, ActivateProductionMessage activateProductionMessage) throws InvalidActionException {
        if(turn.isDoneNormalAction())
            throw new InvalidActionException();
        this.activateProductionMessage = activateProductionMessage;
        calculateResources(turn, activateProductionMessage.getProductions());
        //check coerenza risorse da prendere e input indicati
        if(PaymentHandler.checkCostCoherence(activateProductionMessage.getHowToTakeResources(),inputResources)) {
            if (turn.getPlayer().getPersonalBoard().getWarehouse().checkResources(inputResources)) {
                Warehouse playerWarehouse = turn.getPlayer().getPersonalBoard().getWarehouse();
                try {
                    PaymentHandler.performPayment(playerWarehouse, activateProductionMessage.getHowToTakeResources(), turn);
                    playerWarehouse.addResourcesToStrongbox(outputResources);
                    turn.getPlayer().getPersonalBoard().getFaithTrack().moveMarker(faith);
                    turn.normalActionDone();
                } catch (DepotOutOfBoundsException e) {
                    //TODO HANDLEERROR (DEPOTOUTOFBOUND)
                } catch (DepotNotFoundException e) {
                    //TODO HANDLEERROR (DEPOTNOTFOUND)
                } catch (StrongboxOutOfBoundException e) {
                    //TODO HANDLEERROR (STRONGBOXOUTOFBOUND)
                }
            } else {
                /*model in uno stato di errore e notifica il client*/
            }
        }
        // TODO: else HANDLEERROR(IncoherenceMessage) -> coerenza risorse da prendere e input indicati
    }

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



}
