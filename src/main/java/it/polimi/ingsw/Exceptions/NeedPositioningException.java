package it.polimi.ingsw.Exceptions;


import it.polimi.ingsw.Commons.ResourceType;

import java.util.ArrayList;
import java.util.List;

/**
 * Exception that can be thrown when a player is in a {@link it.polimi.ingsw.Model.TakeResourcesFromMarket} phase and the model
 * detects that it's impossible to settle a resource as specified by player.
 */
public class NeedPositioningException extends Exception {
    private final List<ResourceType> resourcesToSettle = new ArrayList<>();

    /**
     * Adds all resources that couldn't be placed in depots to attribute of exception instance.
     * @param resourcesToSettle resources that require a position.
     */
    public NeedPositioningException(List<ResourceType> resourcesToSettle) {
        super();
        this.resourcesToSettle.addAll(resourcesToSettle);
    }

    public List<ResourceType> getResourcesToSettle() {
        return resourcesToSettle;
    }
}
