package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Card.DevelopmentCard;
import it.polimi.ingsw.Model.Card.Effect;
import it.polimi.ingsw.Utils.ActivateProductionMessage;

import java.util.EnumMap;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

public class ActivateProduction implements AbstractTurnPhase {

    private ActivateProductionMessage activateProductionMessage;
    private Map<ResourceType, Integer> inputResources = new EnumMap<ResourceType, Integer>(ResourceType.class);
    private Map<ResourceType, Integer> outputResources = new EnumMap<ResourceType, Integer>(ResourceType.class);
    private int faith = 0;

    public void activateProduction(Turn turn, ActivateProductionMessage activateProductionMessage) {
        this.activateProductionMessage = activateProductionMessage;
        calculateResources(turn, activateProductionMessage.getProductions());
        if(turn.getPlayer().getPersonalBoard().getWarehouse().checkResources(inputResources)) {

            Warehouse playerWarehouse = turn.getPlayer().getPersonalBoard().getWarehouse();
            PaymentHandler.performPayment(playerWarehouse, activateProductionMessage.getHowToTakeResources(),turn);
            playerWarehouse.addResourcesToStrongbox(outputResources);
            turn.getPlayer().getPersonalBoard().getFaithTrack().moveMarker(faith);

        } else {
            /*model in uno stato di errore e notifica il client*/
        }
    }

    private void calculateResources(Turn turn, ActiveProductions requestedProductions) {
        Stack<DevelopmentCard>[] playerSlots = turn.getPlayer().getPersonalBoard().getSlots();

        if(requestedProductions.isSlot1()) {
            playerSlots[0].peek().getTrade().getInputResources().forEach((key, value) -> inputResources.merge(key,value,Integer::sum));
            playerSlots[0].peek().getTrade().getOutputResources().forEach((key, value) -> outputResources.merge(key,value,Integer::sum));
            faith += playerSlots[0].peek().getTrade().getOutputFaith();
        }
        if(requestedProductions.isSlot2()) {
            playerSlots[1].peek().getTrade().getInputResources().forEach((key, value) -> inputResources.merge(key,value,Integer::sum));
            playerSlots[1].peek().getTrade().getOutputResources().forEach((key, value) -> outputResources.merge(key,value,Integer::sum));
            faith += playerSlots[1].peek().getTrade().getOutputFaith();
        }
        if(requestedProductions.isSlot3()) {
            playerSlots[2].peek().getTrade().getInputResources().forEach((key, value) -> inputResources.merge(key,value,Integer::sum));
            playerSlots[2].peek().getTrade().getOutputResources().forEach((key, value) -> outputResources.merge(key,value,Integer::sum));
            faith += playerSlots[2].peek().getTrade().getOutputFaith();
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
