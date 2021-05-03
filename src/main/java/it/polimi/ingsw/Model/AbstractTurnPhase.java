package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Exceptions.LeaderRequirementsException;
import it.polimi.ingsw.Exceptions.LeaderStatusException;
import it.polimi.ingsw.Exceptions.PaymentErrorException;
import it.polimi.ingsw.Utils.Messages.ClientMessages.*;

public interface AbstractTurnPhase {
    default void leaderAction(Turn turn, LeaderActionMessage message) throws LeaderStatusException, LeaderRequirementsException {
    }

    default void takeResourcesFromMarket(Turn turn, TakeResourcesFromMarketMessage takeResourcesFromMarketMessage) throws InvalidActionException {
        throw new InvalidActionException();
    }

    default void buyDevCard(Turn turn, BuyDevCardMessage message) throws InvalidActionException, PaymentErrorException {
        throw new InvalidActionException();
    }


    default void activateProduction(Turn turn, ActivateProductionMessage message) throws InvalidActionException, PaymentErrorException {
        throw new InvalidActionException();
    }

    default void concludeTurnPhase(Turn turn) {
        //TODO turn.getGame().handleConclusion();
    }

}
