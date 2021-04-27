package it.polimi.ingsw.Model;

public class Turn {
    private final Game game;
    private TurnState turnState;
    private Player player;
    private boolean doneNormalAction;

    public Turn(Game game, Player player) {
        this.game = game;
        this.player = player;
        this.turnState = new TurnState();
    }

    public void setTurnState(TurnState.ActionType actionType) {
        this.turnState.setActionType(actionType);
    }


    public TurnState.ActionType getActionType() { return turnState.getActionType(); }

    public AbstractTurnPhase getTurnPhase() { return turnState.getTurnPhase(); }


    public Player getPlayer() {
        return player;
    }

    public Game getGame() {
        return game;
    }

    public void normalActionDone()
    {
        this.doneNormalAction = true;
    }

    public boolean isDoneNormalAction() {
        return doneNormalAction;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
