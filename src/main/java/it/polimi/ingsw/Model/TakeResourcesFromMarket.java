package it.polimi.ingsw.Model;

public class TakeResourcesFromMarket implements AbstractTurnPhase{

    @Override
    public void takeResourcesFromMarket(Turn turn,String choice, int index)
    {

        turn.getGame().getMarketTray().takeMarbles(choice,index);
    }
}
