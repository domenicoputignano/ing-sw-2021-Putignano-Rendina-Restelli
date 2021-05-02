package it.polimi.ingsw.Utils;

import it.polimi.ingsw.Commons.ColorMarble;
import it.polimi.ingsw.Model.MarketTray.*;
import it.polimi.ingsw.Model.ResourceType;
import it.polimi.ingsw.Utils.Messages.ClientMessages.*;
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
        List<Pair<Marble, MarbleDestination>> wheretoPutMarbles = new ArrayList<>();
        wheretoPutMarbles.add(new Pair<>(new RedMarble(),MarbleDestination.DEPOT1));
        wheretoPutMarbles.add(new Pair<>(new StandardMarble(ColorMarble.YELLOW, ResourceType.coin),MarbleDestination.DEPOT2));
        wheretoPutMarbles.add(new Pair<>(new StandardMarble(ColorMarble.BLUE, ResourceType.shield),MarbleDestination.DEPOT3));
        wheretoPutMarbles.add(new Pair<>(new StandardMarble(ColorMarble.BLUE, ResourceType.shield),MarbleDestination.DEPOT3));
        message.setWhereToPutMarbles(wheretoPutMarbles);
        assertTrue(message.isValidMessage());
    }

    @Test
    void noMarbleEffectsROWko() {
        message.setPlayerChoice(MarketChoice.ROW);
        message.setIndex(2);
        List<Pair<Marble, MarbleDestination>> wheretoPutMarbles = new ArrayList<>();
        wheretoPutMarbles.add(new Pair<>(new RedMarble(),MarbleDestination.DEPOT1));
        wheretoPutMarbles.add(new Pair<>(new StandardMarble(ColorMarble.YELLOW, ResourceType.coin),MarbleDestination.DEPOT2));
        wheretoPutMarbles.add(new Pair<>(new StandardMarble(ColorMarble.BLUE, ResourceType.shield),MarbleDestination.DEPOT3));
        message.setWhereToPutMarbles(wheretoPutMarbles);
        assertFalse(message.isValidMessage());
    }

    @Test
    void fourWhiteMarbles()
    {
        message.setPlayerChoice(MarketChoice.ROW);
        message.setIndex(3);
        List<Pair<Marble, MarbleDestination>> wheretoPutMarbles = new ArrayList<>();
        wheretoPutMarbles.add(new Pair<>(new WhiteMarble(),MarbleDestination.DEPOT1));
        wheretoPutMarbles.add(new Pair<>(new WhiteMarble(),MarbleDestination.DEPOT2));
        wheretoPutMarbles.add(new Pair<>(new WhiteMarble(),MarbleDestination.DEPOT3));
        wheretoPutMarbles.add(new Pair<>(new WhiteMarble(),MarbleDestination.DEPOT3));
        List<Integer> effects = new ArrayList<>();
        effects.add(1);
        effects.add(1);
        effects.add(1);
        effects.add(2);
        message.setWhiteEffects(effects);
        message.setWhereToPutMarbles(wheretoPutMarbles);
        assertTrue(message.isValidMessage());
    }

    @Test
    void noMarbleEffectsCOLUMNok() {
        message.setPlayerChoice(MarketChoice.COLUMN);
        message.setIndex(2);
        List<Pair<Marble, MarbleDestination>> wheretoPutMarbles = new ArrayList<>();
        wheretoPutMarbles.add(new Pair<>(new RedMarble(),MarbleDestination.DEPOT1));
        wheretoPutMarbles.add(new Pair<>(new StandardMarble(ColorMarble.YELLOW, ResourceType.coin),MarbleDestination.DEPOT2));
        wheretoPutMarbles.add(new Pair<>(new StandardMarble(ColorMarble.BLUE, ResourceType.shield),MarbleDestination.DEPOT3));
        message.setWhereToPutMarbles(wheretoPutMarbles);
        assertTrue(message.isValidMessage());
    }

    @Test
    void noMarbleEffectsCOLUMNko() {
        message.setPlayerChoice(MarketChoice.COLUMN);
        message.setIndex(2);
        List<Pair<Marble, MarbleDestination>> wheretoPutMarbles = new ArrayList<>();
        wheretoPutMarbles.add(new Pair<>(new RedMarble(),MarbleDestination.DEPOT1));
        wheretoPutMarbles.add(new Pair<>(new StandardMarble(ColorMarble.YELLOW, ResourceType.coin),MarbleDestination.DEPOT2));
        wheretoPutMarbles.add(new Pair<>(new StandardMarble(ColorMarble.BLUE, ResourceType.shield),MarbleDestination.DEPOT3));
        wheretoPutMarbles.add(new Pair<>(new StandardMarble(ColorMarble.BLUE, ResourceType.shield),MarbleDestination.DEPOT3));
        message.setWhereToPutMarbles(wheretoPutMarbles);
        assertFalse(message.isValidMessage());
    }
    @Test
    void notValidIndexROW() {
        message.setPlayerChoice(MarketChoice.ROW);
        message.setIndex(4);
        List<Pair<Marble, MarbleDestination>> wheretoPutMarbles = new ArrayList<>();
        wheretoPutMarbles.add(new Pair<>(new RedMarble(),MarbleDestination.DEPOT1));
        wheretoPutMarbles.add(new Pair<>(new StandardMarble(ColorMarble.YELLOW, ResourceType.coin),MarbleDestination.DEPOT2));
        wheretoPutMarbles.add(new Pair<>(new StandardMarble(ColorMarble.BLUE, ResourceType.shield),MarbleDestination.DEPOT3));
        wheretoPutMarbles.add(new Pair<>(new StandardMarble(ColorMarble.BLUE, ResourceType.shield),MarbleDestination.DEPOT3));
        message.setWhereToPutMarbles(wheretoPutMarbles);
        assertFalse(message.isValidMessage());
    }
    @Test
    void notValidIndexCOLUMN() {
        message.setPlayerChoice(MarketChoice.COLUMN);
        message.setIndex(5);
        List<Pair<Marble, MarbleDestination>> wheretoPutMarbles = new ArrayList<>();
        wheretoPutMarbles.add(new Pair<>(new RedMarble(),MarbleDestination.DEPOT1));
        wheretoPutMarbles.add(new Pair<>(new StandardMarble(ColorMarble.YELLOW, ResourceType.coin),MarbleDestination.DEPOT2));
        wheretoPutMarbles.add(new Pair<>(new StandardMarble(ColorMarble.BLUE, ResourceType.shield),MarbleDestination.DEPOT3));
        message.setWhereToPutMarbles(wheretoPutMarbles);
        assertFalse(message.isValidMessage());
    }
    @Test
    void notValidWhiteEffects() {
        message.setPlayerChoice(MarketChoice.ROW);
        message.setIndex(3);
        List<Pair<Marble, MarbleDestination>> wheretoPutMarbles = new ArrayList<>();
        wheretoPutMarbles.add(new Pair<>(new WhiteMarble(),MarbleDestination.DEPOT1));
        wheretoPutMarbles.add(new Pair<>(new WhiteMarble(),MarbleDestination.DEPOT2));
        wheretoPutMarbles.add(new Pair<>(new WhiteMarble(),MarbleDestination.DEPOT3));
        wheretoPutMarbles.add(new Pair<>(new WhiteMarble(),MarbleDestination.DEPOT3));
        List<Integer> effects = new ArrayList<>();
        effects.add(3);
        effects.add(1);
        effects.add(1);
        effects.add(0);
        message.setWhiteEffects(effects);
        message.setWhereToPutMarbles(wheretoPutMarbles);
        assertFalse(message.isValidMessage());
    }

    @Test
    void NotValidWhereToPutMarbles() {
        message.setPlayerChoice(MarketChoice.ROW);
        message.setIndex(2);
        List<Pair<Marble, MarbleDestination>> wheretoPutMarbles = new ArrayList<>();
        wheretoPutMarbles.add(new Pair<>(new RedMarble(),MarbleDestination.DEPOT1));
        wheretoPutMarbles.add(new Pair<>(new StandardMarble(ColorMarble.YELLOW, ResourceType.coin),MarbleDestination.DEPOT2));
        wheretoPutMarbles.add(new Pair<>(new StandardMarble(ColorMarble.BLUE, ResourceType.shield),MarbleDestination.DEPOT3));
        wheretoPutMarbles.add(new Pair<>(null,MarbleDestination.DEPOT3));
        message.setWhereToPutMarbles(wheretoPutMarbles);
        assertFalse(message.isValidMessage());
    }
}