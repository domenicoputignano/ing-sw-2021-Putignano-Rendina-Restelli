package it.polimi.ingsw.Utils.Messages.ClientMessages;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.RemoteView;

public class LeaderChoiceMessage implements GameControllerHandleable {
    /**
     * Index of the first card.
     */
    private int leader1ToDiscard;
    /**
     * Index of the second card.
     */
    private int leader2ToDiscard;


    /**
     * Constructor provides indexes of leader cards to discard, first one is bigger than second one
     * to accomplish correct cards deletions.
     * */
    public LeaderChoiceMessage(int firstLeader, int secondLeader) {
        this.leader1ToDiscard = firstLeader;
        this.leader2ToDiscard = secondLeader;
        switchValue();
    }

    /**
     * Returns if the message is valid according to card indexes chosen.
     */
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

    /**
     * Calls right {@link GameController} method assigned to this message handling.
     * @param gameController controller that will handle the message.
     * @param sender remote view that has forwarded the message.
     */
    public void handleMessage(GameController gameController, RemoteView sender) {
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
