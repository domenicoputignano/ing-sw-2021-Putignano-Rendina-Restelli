package it.polimi.ingsw.Client.clientstates;

import it.polimi.ingsw.Client.Checker;
import it.polimi.ingsw.Client.reducedmodel.ReducedPersonalBoard;
import it.polimi.ingsw.Commons.Effect;
import it.polimi.ingsw.Commons.LeaderCard;
import it.polimi.ingsw.Commons.LeaderEffect;
import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Model.ActiveProductions;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ClientMessages.ActivateProductionMessage;
import it.polimi.ingsw.Utils.ResourceSource;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        messageToSend.setProductions(requiredProduction);
        if(index == 0) requiredProduction.setSlot1(status);
        if(index == 1) requiredProduction.setSlot2(status);
        if(index == 2) requiredProduction.setSlot3(status);
    }

    public void clearSlot() {
        requiredProduction.setDefault();
    }
}
