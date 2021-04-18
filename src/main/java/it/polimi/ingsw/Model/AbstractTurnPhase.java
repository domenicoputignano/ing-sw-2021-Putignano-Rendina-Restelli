package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Utils.BuyDevCardMessage;
import it.polimi.ingsw.Utils.TakeResourcesFromMarketMessage;

public interface AbstractTurnPhase {
    default void leaderAction(int index, boolean toDiscard, Turn turn) throws InvalidActionException {
        throw new InvalidActionException();
    }

    default void takeResourcesFromMarket(Turn turn, TakeResourcesFromMarketMessage takeResourcesFromMarketMessage) throws InvalidActionException {
        throw new InvalidActionException();
    }

    default void buyDevCard(Turn turn, BuyDevCardMessage message) throws InvalidActionException {
        throw new InvalidActionException();
    }


    default void activateProduction(Turn turn, ActiveProductions activeProductions) throws InvalidActionException {
        throw new InvalidActionException();
    }

    default void concludeTurnPhase(Turn turn) {
        //TODO turn.getGame().handleConclusion();
    }

}
