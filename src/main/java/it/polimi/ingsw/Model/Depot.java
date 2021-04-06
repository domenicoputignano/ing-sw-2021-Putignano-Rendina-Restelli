package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.DepotOutOfBoundsException;

public interface Depot {
    public void add(int num) throws DepotOutOfBoundsException;
    public void take(int num) throws DepotOutOfBoundsException;
}
