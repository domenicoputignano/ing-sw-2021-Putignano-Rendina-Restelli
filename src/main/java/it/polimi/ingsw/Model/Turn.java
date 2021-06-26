package it.polimi.ingsw.Model;

/**
 * This class represents a turn of the game flow phase of the game.
 * During each turn a player can perform only one normal action between {@link TakeResourcesFromMarket}, {@link ActivateProduction}
 * and {@link BuyDevCard}. Afterwards he can activate or discard a leader card, or he can move the resources in his warehouse.
 * The players that are not in turn can only see parts of the board without actively interacting with them.
 */

public class Turn {
    /**
     * The game instance.
     */
    private final Game game;
    /**
     * The {@link TurnState} instance associated to this turn.
     */
    private final TurnState turnState;
    /**
     * The player in turn.
     */
    private Player player;
    /**
     * Flag that indicates whether the player in turn has already done a normal action for this turn.
     */
    private boolean doneNormalAction;

    public Turn(Game game, Player player) {
        this.game = game;
        this.player = player;
        this.turnState = new TurnState();
    }

    /**
     * Builds the concrete action class by calling the {@link TurnState} factory method.
     * @param actionType the action the player wants to perform.
     */
    public void setTurnState(TurnState.ActionType actionType) {
        this.turnState.setActionType(actionType);
    }

    public TurnState.ActionType getActionType() { return turnState.getActionType(); }

    public AbstractTurnPhase getTurnPhase() { return turnState.getTurnPhase(); }

    /**
     * @return the player in turn.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return the game instance.
     */
    public Game getGame() {
        return game;
    }

    /**
     * Sets the normal action flag when the normal action has been done for this turn.
     */
    public void normalActionDone() {
        this.doneNormalAction = true;
    }

    /**
     * @return whether the normal action has been already done for this turn.
     */
    public boolean isDoneNormalAction() {
        return doneNormalAction;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
