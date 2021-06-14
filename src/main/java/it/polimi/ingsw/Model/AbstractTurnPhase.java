package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Utils.Messages.ClientMessages.*;

/**
 * This interface provides an abstraction of the main actions that can be performed during the game
 */
public interface AbstractTurnPhase {
    default void leaderAction(Turn turn, LeaderActionMessage message) throws LeaderStatusException, LeaderRequirementsException {
    }

    default void takeResourcesFromMarket(Turn turn, TakeResourcesFromMarketMessage takeResourcesFromMarketMessage) throws InvalidActionException, WhiteEffectMismatchException, NeedPositioningException {
        throw new InvalidActionException();
    }

    default void buyDevCard(Turn turn, BuyDevCardMessage message) throws InvalidActionException, PaymentErrorException, ResourceMismatchException, NotEnoughResourcesException {
        throw new InvalidActionException();
    }


    default void activateProduction(Turn turn, ActivateProductionMessage message) throws InvalidActionException, PaymentErrorException, NotEnoughResourcesException, ResourceMismatchException {
        throw new InvalidActionException();
    }

    default void concludeTurnPhase(Turn turn) {
        //TODO turn.getGame().handleEvent();
    }

}
