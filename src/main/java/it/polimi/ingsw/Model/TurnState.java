package it.polimi.ingsw.Model;

import org.graalvm.compiler.core.common.type.ArithmeticOpTable;

public enum TurnState {
    LEADERACTION(new LeaderAction()),
    TAKERESOURCESFROMMARKET(new TakeResourcesFromMarket()),
    BUYDEVCARD(new BuyDevCard()),
    ACTIVATEPRODUCTION(new ActivateProduction());
    private final AbstractTurnPhase abstractTurnPhase;

    TurnState(AbstractTurnPhase abstractTurnPhase)
    {
        this.abstractTurnPhase = abstractTurnPhase;
    }

    public AbstractTurnPhase getTurnPhase()
    {
        return this.abstractTurnPhase;
    }
}
