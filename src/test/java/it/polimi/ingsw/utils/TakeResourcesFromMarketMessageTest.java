package it.polimi.ingsw.utils;

import it.polimi.ingsw.commons.ColorMarble;
import it.polimi.ingsw.model.marketTray.*;
import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.utils.messages.clientMessages.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

class TakeResourcesFromMarketMessageTest {
    TakeResourcesFromMarketMessage message = new TakeResourcesFromMarketMessage();

    @Test
    void noMarbleEffectsROWok() {
        message.setPlayerChoice(MarketChoice.ROW);
        message.setIndex(2);
        message.addWhereToPutMarbles(new Pair<>(new RedMarble().getReducedVersion(),MarbleDestination.DEPOT1));
        message.addWhereToPutMarbles(new Pair<>(new StandardMarble(ColorMarble.YELLOW, ResourceType.coin).getReducedVersion(),MarbleDestination.DEPOT2));
        message.addWhereToPutMarbles(new Pair<>(new StandardMarble(ColorMarble.BLUE, ResourceType.shield).getReducedVersion(),MarbleDestination.DEPOT3));
        message.addWhereToPutMarbles(new Pair<>(new StandardMarble(ColorMarble.BLUE, ResourceType.shield).getReducedVersion(),MarbleDestination.DEPOT3));
        assertTrue(message.isValidMessage());
    }

    @Test
    void noMarbleEffectsROWko() {
        message.setPlayerChoice(MarketChoice.ROW);
        message.setIndex(2);
        message.addWhereToPutMarbles(new Pair<>(new RedMarble().getReducedVersion(),MarbleDestination.DEPOT1));
        message.addWhereToPutMarbles(new Pair<>(new StandardMarble(ColorMarble.YELLOW, ResourceType.coin).getReducedVersion(),MarbleDestination.DEPOT2));
        message.addWhereToPutMarbles(new Pair<>(new StandardMarble(ColorMarble.BLUE, ResourceType.shield).getReducedVersion(),MarbleDestination.DEPOT3));
        assertFalse(message.isValidMessage());
    }

    @Test
    void fourWhiteMarbles()
    {
        message.setPlayerChoice(MarketChoice.ROW);
        message.setIndex(3);
        message.addWhereToPutMarbles(new Pair<>(new WhiteMarble().getReducedVersion(),MarbleDestination.DEPOT1));
        message.addWhereToPutMarbles(new Pair<>(new WhiteMarble().getReducedVersion(),MarbleDestination.DEPOT2));
        message.addWhereToPutMarbles(new Pair<>(new WhiteMarble().getReducedVersion(),MarbleDestination.DEPOT3));
        message.addWhereToPutMarbles(new Pair<>(new WhiteMarble().getReducedVersion(),MarbleDestination.DEPOT3));
        List<Integer> effects = new ArrayList<>();
        effects.add(1);
        effects.add(1);
        effects.add(1);
        effects.add(2);
        message.setWhiteEffects(effects);
        assertTrue(message.isValidMessage());
    }

    @Test
    void noMarbleEffectsCOLUMNok() {
        message.setPlayerChoice(MarketChoice.COLUMN);
        message.setIndex(2);
        message.addWhereToPutMarbles(new Pair<>(new RedMarble().getReducedVersion(),MarbleDestination.DEPOT1));
        message.addWhereToPutMarbles(new Pair<>(new StandardMarble(ColorMarble.YELLOW, ResourceType.coin).getReducedVersion(),MarbleDestination.DEPOT2));
        message.addWhereToPutMarbles(new Pair<>(new StandardMarble(ColorMarble.BLUE, ResourceType.shield).getReducedVersion(),MarbleDestination.DEPOT3));
        assertTrue(message.isValidMessage());
    }

    @Test
    void noMarbleEffectsCOLUMNko() {
        message.setPlayerChoice(MarketChoice.COLUMN);
        message.setIndex(2);
        message.addWhereToPutMarbles(new Pair<>(new RedMarble().getReducedVersion(),MarbleDestination.DEPOT1));
        message.addWhereToPutMarbles(new Pair<>(new StandardMarble(ColorMarble.YELLOW, ResourceType.coin).getReducedVersion(),MarbleDestination.DEPOT2));
        message.addWhereToPutMarbles(new Pair<>(new StandardMarble(ColorMarble.BLUE, ResourceType.shield).getReducedVersion(),MarbleDestination.DEPOT3));
        message.addWhereToPutMarbles(new Pair<>(new StandardMarble(ColorMarble.BLUE, ResourceType.shield).getReducedVersion(),MarbleDestination.DEPOT3));
        assertFalse(message.isValidMessage());
    }
    @Test
    void notValidIndexROW() {
        message.setPlayerChoice(MarketChoice.ROW);
        message.setIndex(4);
        message.addWhereToPutMarbles(new Pair<>(new RedMarble().getReducedVersion(),MarbleDestination.DEPOT1));
        message.addWhereToPutMarbles(new Pair<>(new StandardMarble(ColorMarble.YELLOW, ResourceType.coin).getReducedVersion(),MarbleDestination.DEPOT2));
        message.addWhereToPutMarbles(new Pair<>(new StandardMarble(ColorMarble.BLUE, ResourceType.shield).getReducedVersion(),MarbleDestination.DEPOT3));
        message.addWhereToPutMarbles(new Pair<>(new StandardMarble(ColorMarble.BLUE, ResourceType.shield).getReducedVersion(),MarbleDestination.DEPOT3));
        assertFalse(message.isValidMessage());
    }
    @Test
    void notValidIndexCOLUMN() {
        message.setPlayerChoice(MarketChoice.COLUMN);
        message.setIndex(5);
        message.addWhereToPutMarbles(new Pair<>(new RedMarble().getReducedVersion(),MarbleDestination.DEPOT1));
        message.addWhereToPutMarbles(new Pair<>(new StandardMarble(ColorMarble.YELLOW, ResourceType.coin).getReducedVersion(),MarbleDestination.DEPOT2));
        message.addWhereToPutMarbles(new Pair<>(new StandardMarble(ColorMarble.BLUE, ResourceType.shield).getReducedVersion(),MarbleDestination.DEPOT3));
        assertFalse(message.isValidMessage());
    }
    @Test
    void notValidWhiteEffects() {
        message.setPlayerChoice(MarketChoice.ROW);
        message.setIndex(3);
        message.addWhereToPutMarbles(new Pair<>(new WhiteMarble().getReducedVersion(),MarbleDestination.DEPOT1));
        message.addWhereToPutMarbles(new Pair<>(new WhiteMarble().getReducedVersion(),MarbleDestination.DEPOT2));
        message.addWhereToPutMarbles(new Pair<>(new WhiteMarble().getReducedVersion(),MarbleDestination.DEPOT3));
        message.addWhereToPutMarbles(new Pair<>(new WhiteMarble().getReducedVersion(),MarbleDestination.DEPOT3));
        List<Integer> effects = new ArrayList<>();
        effects.add(3);
        effects.add(1);
        effects.add(1);
        effects.add(0);
        message.setWhiteEffects(effects);
        assertFalse(message.isValidMessage());
    }

    @Test
    void NotValidWhereToPutMarbles() {
        message.setPlayerChoice(MarketChoice.ROW);
        message.setIndex(2);
        message.addWhereToPutMarbles(new Pair<>(new RedMarble().getReducedVersion(),MarbleDestination.DEPOT1));
        message.addWhereToPutMarbles(new Pair<>(new StandardMarble(ColorMarble.YELLOW, ResourceType.coin).getReducedVersion(),MarbleDestination.DEPOT2));
        message.addWhereToPutMarbles(new Pair<>(new StandardMarble(ColorMarble.BLUE, ResourceType.shield).getReducedVersion(),MarbleDestination.DEPOT3));
        message.addWhereToPutMarbles(new Pair<>(null,MarbleDestination.DEPOT3));
        assertFalse(message.isValidMessage());
    }
}