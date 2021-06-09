package it.polimi.ingsw.Client.view.GUI;

import it.polimi.ingsw.Commons.Effect;
import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Pair;

import java.util.List;

public interface PaymentController {


    public void setVisibleFirstResource();
    public void setVisibleSecondResource();
    public void setVisibleThirdResource();

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

}
