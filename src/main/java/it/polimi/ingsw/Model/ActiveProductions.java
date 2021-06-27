package it.polimi.ingsw.Model;

import java.io.Serializable;


/**
 * This class represents all the productions that can be requested by a player in a turn.
 */
public class ActiveProductions implements Serializable {
    private boolean slot1;
    private boolean slot2;
    private boolean slot3;
    private boolean basic;
    private boolean extraSlot1;
    private boolean extraSlot2;


    public void setDefault() {
        this.slot1 = false;
        this.slot2 = false;
        this.slot3 = false;
        this.basic = false;
        this.extraSlot1 = false;
        this.extraSlot2 = false;
    }


    public void setSlot1(boolean slot1) {
        this.slot1 = slot1;
    }

    public void setSlot2(boolean slot2) {
        this.slot2 = slot2;
    }

    public void setSlot3(boolean slot3) {
        this.slot3 = slot3;
    }

    public void setBasic(boolean basic) {
        this.basic = basic;
    }

    public void setExtraSlot1(boolean extraSlot1) {
        this.extraSlot1 = extraSlot1;
    }

    public void setExtraSlot2(boolean extraSlot2) {
        this.extraSlot2 = extraSlot2;
    }

    public boolean isSlot1() {
        return slot1;
    }

    public boolean isSlot2() {
        return slot2;
    }

    public boolean isSlot3() {
        return slot3;
    }

    public boolean isBasic() {
        return basic;
    }

    public boolean isExtraSlot1() {
        return extraSlot1;
    }

    public boolean isExtraSlot2() {
        return extraSlot2;
    }

    public boolean noneSlotSelected() {
        return !slot1&&!slot2&&!slot3
                &&!extraSlot1&&!extraSlot2&&!basic;
    }

    public String toString() {
        return "Slot1 = " +slot1+" Slot2 = "+slot2+" Slot3 = "+slot3+
                " Basic = "+basic+" ExtraSlot1 "+extraSlot1+" ExtraSlot2 "+extraSlot2;
    }

}
