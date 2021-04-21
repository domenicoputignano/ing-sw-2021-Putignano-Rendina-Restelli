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

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TakeResourcesFromMarketTest {

    Game game;
    @BeforeEach
    void initialization()
    {
        List<Player> playerList = new ArrayList<>();
        playerList.add(new Player("Piero"));
        playerList.add(new Player("Domenico"));
        playerList.add(new Player("Andrea"));
        game = new Game(playerList.get(0),playerList,playerList.get(0),playerList.size());
        game.setup();
        for(Player p: game.getPlayerList())
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
            System.out.print(game.getMarketTray().getAvailableMarbles()[i][j]);
        System.out.println();
        }
        marbles = game.getMarketTray().peekMarbles(MarketChoice.ROW, 2);
        System.out.println(marbles);
        List<Pair<Marble, MarbleDestination>> pairList = new ArrayList<>();
        for(Marble m: marbles)
        {
            pairList.add(new Pair<Marble, MarbleDestination>(m,MarbleDestination.DISCARD));
        }
        takeResourcesFromMarketMessage.setWhereToPutMarbles(pairList);
        game.getTurn().setTurnState(TurnState.TAKERESOURCESFROMMARKET);
        game.getTurn().getTurnState().getTurnPhase().takeResourcesFromMarket(game.getTurn(),takeResourcesFromMarketMessage);
        TakeResourcesFromMarket takeResourcesFromMarket = (TakeResourcesFromMarket) game.getTurn().getTurnState().getTurnPhase();
        System.out.println(takeResourcesFromMarket.getWhereToPutResources());
        System.out.println(takeResourcesFromMarket.getFaith());
    }

    @Test
    void checkValidWhiteEffects() throws InvalidActionException {
        TakeResourcesFromMarketMessage message = new TakeResourcesFromMarketMessage();
        message.setPlayerChoice(MarketChoice.ROW, 2);
        game.getTurn().getPlayer().getLeaderCards().clear();
        game.getTurn().getPlayer().getLeaderCards().add(new LeaderCard(new LeaderEffect(Effect.CMARBLE,ResourceType.shield),null,null,0));
        game.getTurn().getPlayer().getLeaderCards().add(new LeaderCard(new LeaderEffect(Effect.CMARBLE,ResourceType.coin),null,null,0));
        game.getTurn().getPlayer().getLeaderCards().get(0).setIsActive();
        game.getTurn().getPlayer().getLeaderCards().get(1).setIsActive();
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
        game.getTurn().setTurnState(TurnState.TAKERESOURCESFROMMARKET);
        game.getTurn().getTurnState().getTurnPhase().takeResourcesFromMarket(game.getTurn(),message);
        TakeResourcesFromMarket takeResourcesFromMarket = (TakeResourcesFromMarket) game.getTurn().getTurnState().getTurnPhase();
        assertTrue(takeResourcesFromMarket.checkValidWhiteEffects(game.getTurn(), message.getWhiteEffects(),message.getRequestedMarbles()));
    }

}