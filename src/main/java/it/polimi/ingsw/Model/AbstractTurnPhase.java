package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.InvalidActionException;

public interface AbstractTurnPhase {
    default void leaderAction(int index, boolean toDiscard, Turn turn) throws InvalidActionException {
        throw new InvalidActionException();
    }

    default void takeResourcesFromMarket(Turn turn,String choice, int index) throws InvalidActionException {
        throw new InvalidActionException();
    }

    default void buyDevCard(Turn turn) throws InvalidActionException {
        throw new InvalidActionException();
    }

    default void activateProduction(Turn turn) throws InvalidActionException {
        throw new InvalidActionException();
    }

}
