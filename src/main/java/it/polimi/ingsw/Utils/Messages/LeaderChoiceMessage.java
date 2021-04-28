package it.polimi.ingsw.Utils.Messages;

public class LeaderChoiceMessage {
    private int leader1ToHold;
    private int leader2ToHold;

    public LeaderChoiceMessage(int firstLeader, int secondLeader) {
        this.leader1ToHold = firstLeader;
        this.leader2ToHold = secondLeader;
        switchValue();
    }

    public boolean isValidMessage() {
        if(leader2ToHold == leader1ToHold) return false;
        if(leader2ToHold < 1 || leader2ToHold > 4 || leader1ToHold < 1 || leader1ToHold > 4) return false;
        return true;
    }

    public int getLeader1ToHold() {
        return leader1ToHold;
    }

    public int getLeader2ToHold() {
        return leader2ToHold;
    }



    private void switchValue() {
        if(leader1ToHold < leader2ToHold) {
            int temp = leader1ToHold;
            leader1ToHold = leader2ToHold;
            leader2ToHold = temp;
        }
    }
}
