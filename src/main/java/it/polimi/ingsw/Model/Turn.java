package it.polimi.ingsw.Model;

public class Turn {
    private Game game;
    private TurnState turnState;
    private Player player;
    private boolean doneNormalAction;

    public void setTurnState(TurnState turnState)
    {
        this.turnState = turnState;
    }

    public TurnState getTurnState() {
        return turnState;
    }

    public Player getPlayer() {
        return player;
    }

    public Game getGame() {
        return game;
    }


}
