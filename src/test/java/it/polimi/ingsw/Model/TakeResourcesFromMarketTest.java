package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Model.MarketTray.Marble;
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

}