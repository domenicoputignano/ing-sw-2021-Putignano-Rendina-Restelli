package it.polimi.ingsw.Model;

import it.polimi.ingsw.Utils.Messages.LeaderActionMessage;

public class LeaderAction implements AbstractTurnPhase{

    @Override
    public void leaderAction(Turn turn, LeaderActionMessage message) {
        Player player = turn.getPlayer();
        if(player.checkLeaderStatus(message.getIndex()-1)) {
            if (message.isToDiscard())
                player.discardLeaderCard(message.getIndex() - 1);
            else {
                if (player.checkLeaderActivation(message.getIndex() - 1))
                    player.activateLeaderCard(message.getIndex() - 1);
                //else TODO: HANDLEERROR(REQUISITI CARTA LEADER NON SODDISFATTI)
            }
        }
        //else TODO: HANDLEERROR(AZIONE LEADER NON PERFORMABILE)

    }

}
