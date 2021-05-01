package it.polimi.ingsw.Utils;

import org.junit.jupiter.api.Test;
import it.polimi.ingsw.Utils.Messages.ClientMessages.*;
import static org.junit.jupiter.api.Assertions.*;


class LeaderActionMessageTest {

    LeaderActionMessage message1 = new LeaderActionMessage();
    LeaderActionMessage message2 = new LeaderActionMessage();

    @Test
    void setIndex() {
        message1.setIndex(1);
        assertEquals(1, message1.getIndex());
    }

    @Test
    void setToDiscard() {
        message1.setToDiscard(true);
        assertTrue(message1.isToDiscard());
        message2.setToDiscard(false);
        assertFalse(message2.isToDiscard());
    }

    @Test
    void isValidMessage() {
        message1.setIndex(28);
        assertFalse(message1.isValidMessage());
        message2.setIndex(1);
        assertTrue(message2.isValidMessage());
        message2.setIndex(-24);
        assertFalse(message2.isValidMessage());
    }
}