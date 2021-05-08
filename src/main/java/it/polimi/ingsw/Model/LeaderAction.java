package it.polimi.ingsw.Model;

import it.polimi.ingsw.Commons.LeaderCard;
import it.polimi.ingsw.Exceptions.LeaderRequirementsException;
import it.polimi.ingsw.Exceptions.LeaderStatusException;
import it.polimi.ingsw.Utils.Messages.ClientMessages.*;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.LeaderActionUpdate;

public class LeaderAction implements AbstractTurnPhase{

    @Override
    public void leaderAction(Turn turn, LeaderActionMessage message) throws LeaderStatusException, LeaderRequirementsException {
        Player player = turn.getPlayer();
        if(player.checkLeaderStatus(message.getIndex()-1)) {
            if (message.isToDiscard()) {
                LeaderCard discarded = player.discardLeaderCard(message.getIndex() - 1);
                turn.getGame().notifyUpdate(new LeaderActionUpdate(turn.getPlayer().getUser(),
                        player.getPersonalBoard().getReducedVersion(),
                        discarded, message.getIndex(),true));
            }
            else {
                if (player.checkLeaderActivation(message.getIndex() - 1)) {
                    player.activateLeaderCard(message.getIndex() - 1);
                    turn.getGame().notifyUpdate(new LeaderActionUpdate(turn.getPlayer().getUser(),
                            player.getReducedPersonalBoard(),
                            player.getLeaderCards().get(message.getIndex() - 1),
                            message.getIndex(),false));
                }
                else throw new LeaderRequirementsException();
            }
        } else throw new LeaderStatusException();
    }

}
