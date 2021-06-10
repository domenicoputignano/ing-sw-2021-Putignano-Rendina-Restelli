package it.polimi.ingsw.Client.view.GUI;

import it.polimi.ingsw.Commons.Effect;
import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Pair;
import it.polimi.ingsw.Utils.ResourceSource;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PaymentController {


    void setVisibleFirstResource();
    void setVisibleSecondResource();
    void setVisibleThirdResource();

    default void showRequiredResources(List<Pair<ResourceType,Integer>> neededResources) {
        for(int i = 0; i < neededResources.size(); i++) {
            if(i == 0) setVisibleFirstResource();
            if(i == 1) setVisibleSecondResource();
            if(i == 2) setVisibleThirdResource();
        }
    }

    default boolean isAvailableExtraDepotOfType(ResourceType resource, Client client) {
        return client.getGame().getPlayer(client.getUser()).getPersonalBoard().isAvailableEffectOfType(Effect.EXTRADEPOT,resource);
    }

    default boolean isPaymentDone(List<Pair<ResourceType,Integer>> requiredResources) {
        return requiredResources.stream().allMatch(x -> x.getValue() == 0);
    }

    default Map<ResourceSource, EnumMap<ResourceType, Integer>> initializeMap() {
        Map<ResourceSource, EnumMap<ResourceType,Integer>> paymentInstruction = new HashMap<>();
        paymentInstruction.put(ResourceSource.DEPOT, new EnumMap<>(ResourceType.class));
        paymentInstruction.put(ResourceSource.STRONGBOX, new EnumMap<>(ResourceType.class));
        paymentInstruction.put(ResourceSource.EXTRA, new EnumMap<>(ResourceType.class));
        return paymentInstruction;
    }

    default void createInstructionEntry(Map<ResourceSource, EnumMap<ResourceType,Integer>> paymentInstruction, List<Pair<ResourceType,Integer>> neededResources,
                                        int resourceIndex,int sourceDepot, int sourceStrongbox, int sourceExtra) {
        if(sourceDepot > 0) {
            paymentInstruction.get(ResourceSource.DEPOT).put(neededResources.get(resourceIndex).getKey(),sourceDepot);
        }
        if(sourceStrongbox > 0) {
            paymentInstruction.get(ResourceSource.STRONGBOX).put(neededResources.get(resourceIndex).getKey(),sourceStrongbox);
        }
        if(sourceExtra > 0) {
            paymentInstruction.get(ResourceSource.EXTRA).put(neededResources.get(resourceIndex).getKey(),sourceExtra);
        }
    }

    default void makeTextInvisible(Text text) {
        text.setVisible(false);
    }


    default boolean editedCostList(List<Pair<ResourceType,Integer>> neededResources,int resourceIndex, int taken, int currentValue, TextField resource) {
        int required = neededResources.get(resourceIndex-1).getValue();
        if(required>=taken) {
            hideError();
            neededResources.get(resourceIndex-1).setValue(required-taken);
            resource.setText(""+currentValue);
            showFinishButton();
            return true;
        } else {
            setErrorDevTextKO("Maximum number selected!");
            showFinishButton();
            return false;
        }
    }

    default boolean addToCostList(List<Pair<ResourceType,Integer>> neededResources,int resourceIndex, int taken, int currentValue, TextField resource) {
        if(currentValue >= 0) {
            neededResources.get(resourceIndex-1).setValue(neededResources.get(resourceIndex-1).getValue()+taken);
            resource.setText(""+currentValue);
            showFinishButton();
            return true;
        } else {
            showFinishButton();
            return false;
        }
    }



    void showFinishButton();
    void hideError();
    void setErrorDevTextKO(String s);

}
