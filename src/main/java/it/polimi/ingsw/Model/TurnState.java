package it.polimi.ingsw.Model;

public class TurnState {
    private ActionType actionType;
    private AbstractTurnPhase abstractTurnPhase;

    public enum ActionType {
        LEADERACTION, TAKERESOURCESFROMMARKET, BUYDEVCARD, ACTIVATEPRODUCTION
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
        this.abstractTurnPhase = buildConcreteAction();
    }

    public ActionType getActionType() { return actionType ; }

    public AbstractTurnPhase getTurnPhase()
    {
        return this.abstractTurnPhase;
    }

    private AbstractTurnPhase buildConcreteAction() {
        if(actionType == ActionType.LEADERACTION) return new LeaderAction();
        if(actionType == ActionType.ACTIVATEPRODUCTION) return new ActivateProduction();
        if(actionType == ActionType.BUYDEVCARD) return new BuyDevCard();
        if(actionType == ActionType.TAKERESOURCESFROMMARKET) return new TakeResourcesFromMarket();
        else return null;
    }
}
