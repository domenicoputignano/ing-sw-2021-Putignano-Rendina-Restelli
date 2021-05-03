package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.LeaderRequirementsException;
import it.polimi.ingsw.Exceptions.LeaderStatusException;
import it.polimi.ingsw.Utils.Messages.ClientMessages.*;

public class LeaderAction implements AbstractTurnPhase{

    @Override
    public void leaderAction(Turn turn, LeaderActionMessage message) throws LeaderStatusException, LeaderRequirementsException {
        Player player = turn.getPlayer();
        if(player.checkLeaderStatus(message.getIndex()-1)) {
            if (message.isToDiscard())
                player.discardLeaderCard(message.getIndex() - 1);
            else {
                if (player.checkLeaderActivation(message.getIndex() - 1))
                    player.activateLeaderCard(message.getIndex() - 1);
                else throw new LeaderRequirementsException();
            }
        } else throw new LeaderStatusException();

    }

}
