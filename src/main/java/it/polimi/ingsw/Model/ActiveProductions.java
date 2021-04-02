package it.polimi.ingsw.Model;

public class ActiveProductions {
    private boolean slot1;
    private boolean slot2;
    private boolean slot3;
    private boolean basic;
    private boolean extraslot1;
    private boolean extraslot2;


    public void setDefault() {
        this.slot1 = false;
        this.slot2 = false;
        this.slot3 = false;
        this.basic = false;
        this.extraslot1 = false;
        this.extraslot2 = false;
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

    public void setExtraslot1(boolean extraslot1) {
        this.extraslot1 = extraslot1;
    }

    public void setExtraslot2(boolean extraslot2) {
        this.extraslot2 = extraslot2;
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

    public boolean isExtraslot1() {
        return extraslot1;
    }

    public boolean isExtraslot2() {
        return extraslot2;
    }
}
