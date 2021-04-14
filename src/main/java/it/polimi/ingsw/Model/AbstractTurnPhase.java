package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Utils.BuyDevCardMessage;

public interface AbstractTurnPhase {
    default void leaderAction(int index, boolean toDiscard, Turn turn) throws InvalidActionException {
        throw new InvalidActionException();
    }

    default void takeResourcesFromMarket(Turn turn,String choice, int index) throws InvalidActionException {
        throw new InvalidActionException();
    }

    default void buyDevCard(Turn turn, BuyDevCardMessage message) throws InvalidActionException {
        throw new InvalidActionException();
    }

    default void activateProduction(Turn turn) throws InvalidActionException {
        throw new InvalidActionException();
    }

}
