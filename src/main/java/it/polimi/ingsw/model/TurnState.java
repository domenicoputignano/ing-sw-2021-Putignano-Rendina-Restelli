package it.polimi.ingsw.model;

/**
 * This class encapsulates the creation of the main action classes using a custom factory pattern.
 * Since only one instance of the action classes can be instantiated at the same time, this
 * class permits to specify the action the player in turn wants to perform and to instantiate the correspondent
 * concrete class.
 */

public class TurnState {
    /**
     * The action chosen.
     */
    private ActionType actionType;
    /**
     * The concrete class of the action chosen.
     */
    private AbstractTurnPhase abstractTurnPhase;
    /**
     * Enum containing all the possible actions the player can perform during his turn.
     */
    public enum ActionType {
        LEADERACTION, TAKERESOURCESFROMMARKET, BUYDEVCARD, ACTIVATEPRODUCTION
    }

    /**
     * Facade method which receives the action the player wants to perform and builds the correspondent
     * concrete class.
     * @param actionType the action the player wants to perform.
     */
    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
        this.abstractTurnPhase = buildConcreteAction();
    }

    /**
     * Build the concrete class of the action from the actionType chosen by the player.
     * @return the concrete class of the action built.
     */
    private AbstractTurnPhase buildConcreteAction() {
        if(actionType == ActionType.LEADERACTION) return new LeaderAction();
        if(actionType == ActionType.ACTIVATEPRODUCTION) return new ActivateProduction();
        if(actionType == ActionType.BUYDEVCARD) return new BuyDevCard();
        if(actionType == ActionType.TAKERESOURCESFROMMARKET) return new TakeResourcesFromMarket();
        else return null;
    }

    public ActionType getActionType() { return actionType ; }

    public AbstractTurnPhase getTurnPhase() {
        return this.abstractTurnPhase;
    }
}
