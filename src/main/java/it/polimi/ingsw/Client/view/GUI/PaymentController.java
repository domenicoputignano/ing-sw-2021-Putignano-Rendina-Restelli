package it.polimi.ingsw.Client.view.GUI;

import it.polimi.ingsw.Commons.Effect;
import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Pair;
import it.polimi.ingsw.Utils.ResourceSource;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Interface that allows to handle player's decision while performing an action that requires picking resources
 * from warehouse.
 */
public interface PaymentController {

    void setVisibleFirstResource();
    void setVisibleSecondResource();
    void setVisibleThirdResource();

    /**
     * Method to display buttons and image related to resources needed to perform an action.
     */
    default void showRequiredResources(List<Pair<ResourceType,Integer>> neededResources) {
        for(int i = 0; i < neededResources.size(); i++) {
            if(i == 0) setVisibleFirstResource();
            if(i == 1) setVisibleSecondResource();
            if(i == 2) setVisibleThirdResource();
        }
    }

    /**
     * Boolean method that checks if an extra depot is available for the client that is performing the action.
     */
    default boolean isAvailableExtraDepotOfType(ResourceType resource, Client client) {
        return client.getGame().getPlayer(client.getUser()).getPersonalBoard().isAvailableEffectOfType(Effect.EXTRADEPOT,resource);
    }

    /**
     * Boolean method that checks if map representing cost of the action has been reset.
     */
    default boolean isPaymentDone(List<Pair<ResourceType,Integer>> requiredResources) {
        return requiredResources.stream().allMatch(x -> x.getValue() == 0);
    }

    /**
     * Initializes and returns a map containing three entries corresponding to resources that have to be picked from
     * depots, extra depots and strongbox.
     */
    default Map<ResourceSource, EnumMap<ResourceType, Integer>> initializeMap() {
        Map<ResourceSource, EnumMap<ResourceType,Integer>> paymentInstruction = new HashMap<>();
        paymentInstruction.put(ResourceSource.DEPOT, new EnumMap<>(ResourceType.class));
        paymentInstruction.put(ResourceSource.STRONGBOX, new EnumMap<>(ResourceType.class));
        paymentInstruction.put(ResourceSource.EXTRA, new EnumMap<>(ResourceType.class));
        return paymentInstruction;
    }

    /**
     * Create an entry in a map that represents how the resources have to be picked from player's warehouse.
     * @param paymentInstruction map representing how to take resources.
     * @param neededResources list containing for each resource type how many occurrences are required.
     * @param resourceIndex index of pair inside needed resources parameter.
     * @param sourceDepot occurrences of resource type to take from depots.
     * @param sourceStrongbox occurrences of resource type to take from strongbox.
     * @param sourceExtra occurrences of resource type to take from extra depots.
     */
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

    /**
     * Method that modifies its first parameter according to player's selection.
     * @param neededResources list possibly edited.
     * @param resourceIndex index of list element.
     * @param taken number of resources taken.
     * @param currentValue occurrences of needed resource type.
     * @param resource text field that displays current value of occurrences selected.
     * @return true if the list corresponding to the first parameter of the method has been edited.
     */
    default boolean editedCostList(List<Pair<ResourceType,Integer>> neededResources,int resourceIndex, int taken, int currentValue, TextField resource) {
        int required = neededResources.get(resourceIndex-1).getValue();
        if(required>=taken) {
            hideError();
            neededResources.get(resourceIndex-1).setValue(required-taken);
            resource.setText(""+currentValue);
            showFinishButton();
            return true;
        } else {
            maxOccurrencesSet("Maximum number selected!");
            showFinishButton();
            return false;
        }
    }

    /**
     * Method that adds some occurrences related to a certain resource type in list given as the first parameter
     * after that a player has clicked on a minus button.
     * @param neededResources list to edit.
     * @param resourceIndex index of resource type related to selection.
     * @param taken number of occurrences subtracted to current value.
     * @param currentValue number of occurrences currently selected.
     * @param resource text field that displays current value of occurrences selected.
     * @return true if some occurrences have been added to the costs list.
     */
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



    default void closeAction(Button concludeButton) {
        Stage stage = (Stage) concludeButton.getScene().getWindow();
        stage.close();
    }


    void showFinishButton();

    void hideError();

    /**
     * Displays that maximum number of occurrences for a certain resource type has been selected.
     * @param s string to be displayed.
     */
    void maxOccurrencesSet(String s);

}
