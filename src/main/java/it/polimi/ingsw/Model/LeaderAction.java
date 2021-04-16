package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.InvalidActionException;

public class LeaderAction implements AbstractTurnPhase{

    @Override
    public void leaderAction(int index, boolean toDiscard, Turn turn) {
        Player player = turn.getPlayer();
        if(toDiscard)
            player.discardLeaderCard(index - 1);
        else
            /*TODO: aggiungere da qualche parte check sui requirements della carta leader*/
            player.activateLeaderCard(index-1);
    }


}
