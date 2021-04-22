package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Model.Card.Effect;
import it.polimi.ingsw.Model.Card.LeaderCard;
import it.polimi.ingsw.Model.Card.LeaderEffect;
import it.polimi.ingsw.Model.MarketTray.Marble;
import it.polimi.ingsw.Model.MarketTray.WhiteMarble;
import it.polimi.ingsw.Utils.MarbleDestination;
import it.polimi.ingsw.Utils.MarketChoice;
import it.polimi.ingsw.Utils.Pair;
import it.polimi.ingsw.Utils.TakeResourcesFromMarketMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TakeResourcesFromMarketTest {

    MultiPlayerMode multiPlayerMode;
    @BeforeEach
    void initialization()
    {
        List<Player> playerList = new ArrayList<>();
        playerList.add(new Player("Piero"));
        playerList.add(new Player("Domenico"));
        playerList.add(new Player("Andrea"));
        multiPlayerMode = new MultiPlayerMode(playerList.get(0),playerList,playerList.get(0),playerList.size());
        multiPlayerMode.setup();
        for(Player p: multiPlayerMode.getPlayerList())
        {
            p.getLeaderCards().remove(2);
            p.getLeaderCards().remove(2);
        }
    }
    @Test
    void takeResourcesFromMarket() throws InvalidActionException {
        List<Marble> marbles;
        TakeResourcesFromMarketMessage takeResourcesFromMarketMessage = new TakeResourcesFromMarketMessage();
        takeResourcesFromMarketMessage.setPlayerChoice(MarketChoice.ROW, 2);
        for (int i = 0; i < 3; i++)
        { for (int j = 0; j < 4; j++)
            System.out.print(multiPlayerMode.getMarketTray().getAvailableMarbles()[i][j]);
        System.out.println();
        }
        marbles = multiPlayerMode.getMarketTray().peekMarbles(MarketChoice.ROW, 2);
        System.out.println(marbles);
        List<Pair<Marble, MarbleDestination>> pairList = new ArrayList<>();
        for(Marble m: marbles)
        {
            pairList.add(new Pair<Marble, MarbleDestination>(m,MarbleDestination.DISCARD));
        }
        takeResourcesFromMarketMessage.setWhereToPutMarbles(pairList);
        multiPlayerMode.getTurn().setTurnState(TurnState.TAKERESOURCESFROMMARKET);
        multiPlayerMode.getTurn().getTurnState().getTurnPhase().takeResourcesFromMarket(multiPlayerMode.getTurn(),takeResourcesFromMarketMessage);
        TakeResourcesFromMarket takeResourcesFromMarket = (TakeResourcesFromMarket) multiPlayerMode.getTurn().getTurnState().getTurnPhase();
        System.out.println(takeResourcesFromMarket.getWhereToPutResources());
        System.out.println(takeResourcesFromMarket.getFaith());
    }

    @Test
    void checkValidWhiteEffects() throws InvalidActionException {
        TakeResourcesFromMarketMessage message = new TakeResourcesFromMarketMessage();
        message.setPlayerChoice(MarketChoice.ROW, 2);
        multiPlayerMode.getTurn().getPlayer().getLeaderCards().clear();
        multiPlayerMode.getTurn().getPlayer().getLeaderCards().add(new LeaderCard(new LeaderEffect(Effect.CMARBLE,ResourceType.shield),null,null,0));
        multiPlayerMode.getTurn().getPlayer().getLeaderCards().add(new LeaderCard(new LeaderEffect(Effect.CMARBLE,ResourceType.coin),null,null,0));
        multiPlayerMode.getTurn().getPlayer().getLeaderCards().get(0).setIsActive();
        multiPlayerMode.getTurn().getPlayer().getLeaderCards().get(1).setIsActive();
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
        multiPlayerMode.getTurn().setTurnState(TurnState.TAKERESOURCESFROMMARKET);
        multiPlayerMode.getTurn().getTurnState().getTurnPhase().takeResourcesFromMarket(multiPlayerMode.getTurn(),message);
        TakeResourcesFromMarket takeResourcesFromMarket = (TakeResourcesFromMarket) multiPlayerMode.getTurn().getTurnState().getTurnPhase();
        assertTrue(takeResourcesFromMarket.checkValidWhiteEffects(multiPlayerMode.getTurn(), message.getWhiteEffects(),message.getRequestedMarbles()));
    }

}