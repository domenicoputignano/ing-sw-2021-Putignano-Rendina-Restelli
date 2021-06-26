package it.polimi.ingsw.Model;

import it.polimi.ingsw.Commons.LeaderCard;
import it.polimi.ingsw.Exceptions.LeaderRequirementsException;
import it.polimi.ingsw.Exceptions.LeaderStatusException;
import it.polimi.ingsw.Utils.Messages.ClientMessages.*;
import it.polimi.ingsw.Utils.Messages.ServerMessages.Updates.LeaderActionUpdate;

/**
 * This class implements one of the possible main action of the game turn.
 * At the beginning or at the end of the turn a player can activate or discard one of the {@link LeaderCard} he owns.
 */

public class LeaderAction implements AbstractTurnPhase{

    /**
     * Performs the leader action by calling the proper method of the {@link Player} class.
     * Before all a check on the leader card status is done. Only a not active card can be discarded or activated.
     * If the player wants to discard the card, no further checks are performed and the proper method is called.
     * If the player wants to activate the card instead, a check whether he fulfills the requirements needed
     * to activate the card is performed. If this check is passed, the card is activated by calling the proper method.
     * At the end, all remote views are notified of the overcome of the action with a proper update.
     * @param turn the turn in which the action is being performed.
     * @param message sent by the client containing the instructions on how to perform the action.
     * @throws LeaderStatusException if the leader card specified is already active.
     * @throws LeaderRequirementsException if the requirements needed to activate the card aren't fulfilled.
     */
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
