package it.polimi.ingsw.Utils.Messages.ClientMessages;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Model.Player;

public class LeaderChoiceMessage implements GameControllerHandleable {
    private int leader1ToDiscard;
    private int leader2ToDiscard;


    /* The constructor provides indexes of LeaderCard to discard, first one is bigger than second one
    to accomplish correct deletion of cards */
    public LeaderChoiceMessage(int firstLeader, int secondLeader) {
        this.leader1ToDiscard = firstLeader;
        this.leader2ToDiscard = secondLeader;
        switchValue();
    }

    public boolean isValidMessage() {
        if(leader2ToDiscard == leader1ToDiscard) return false;
        if(leader2ToDiscard < 1 || leader2ToDiscard > 4 || leader1ToDiscard < 1 || leader1ToDiscard > 4) return false;
        return true;
    }

    public int getLeader1ToDiscard() {
        return leader1ToDiscard;
    }

    public int getLeader2ToDiscard() {
        return leader2ToDiscard;
    }

    public void handleMessage(GameController gameController, Player sender) {
        gameController.handleLeaderChoiceMessage(this, sender);
    }


    private void switchValue() {
        if(leader1ToDiscard < leader2ToDiscard) {
            int temp = leader1ToDiscard;
            leader1ToDiscard = leader2ToDiscard;
            leader2ToDiscard = temp;
        }
    }
}
