package it.polimi.ingsw.Utils;

public class LeaderActionMessage {
    private int index;
    private boolean toDiscard;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isToDiscard() {
        return toDiscard;
    }

    public void setToDiscard(boolean toDiscard) {
        this.toDiscard = toDiscard;
    }

    public boolean isValidMessage()
    {
        if(index<=0 || index>2) return false;
        return true;
    }
}
