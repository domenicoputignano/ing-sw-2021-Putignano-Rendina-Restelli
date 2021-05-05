package it.polimi.ingsw.Exceptions;


import it.polimi.ingsw.Model.ResourceType;

import java.util.ArrayList;
import java.util.List;

public class NeedPositioningException extends Exception {
    private final List<ResourceType> resourcesToSettle = new ArrayList<>();

    public NeedPositioningException(List<ResourceType> resourcesToSettle) {
        super();
        this.resourcesToSettle.addAll(resourcesToSettle);
    }

    public List<ResourceType> getResourcesToSettle() {
        return resourcesToSettle;
    }
}
