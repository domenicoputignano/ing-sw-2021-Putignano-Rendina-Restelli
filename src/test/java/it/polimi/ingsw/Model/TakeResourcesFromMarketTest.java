package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.DepotOutOfBoundsException;
import it.polimi.ingsw.Exceptions.IncompatibleResourceTypeException;
import it.polimi.ingsw.Exceptions.InvalidActionException;
import it.polimi.ingsw.Commons.Effect;
import it.polimi.ingsw.Commons.LeaderCard;
import it.polimi.ingsw.Commons.LeaderEffect;
import it.polimi.ingsw.Commons.ColorMarble;
import it.polimi.ingsw.Exceptions.WhiteEffectMismatchException;
import it.polimi.ingsw.Model.MarketTray.Marble;
import it.polimi.ingsw.Model.MarketTray.WhiteMarble;
import it.polimi.ingsw.Utils.MarbleDestination;
import it.polimi.ingsw.Utils.MarketChoice;
import it.polimi.ingsw.Utils.Pair;
import it.polimi.ingsw.Utils.Messages.ClientMessages.*;
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
    void takeResourcesFromMarket() throws InvalidActionException, WhiteEffectMismatchException {
        List<Marble> marbles;
        TakeResourcesFromMarketMessage takeResourcesFromMarketMessage = new TakeResourcesFromMarketMessage();
        takeResourcesFromMarketMessage.setPlayerChoice(MarketChoice.ROW, 2);
        multiPlayerMode.getTurn().getPlayer().getLeaderCards().clear();
        multiPlayerMode.getTurn().getPlayer().getLeaderCards().add(new LeaderCard(new LeaderEffect(Effect.CMARBLE,ResourceType.shield),null,null,0));
        multiPlayerMode.getTurn().getPlayer().getLeaderCards().add(new LeaderCard(new LeaderEffect(Effect.CMARBLE,ResourceType.coin),null,null,0));
        multiPlayerMode.getTurn().getPlayer().getLeaderCards().get(0).setIsActive();
        multiPlayerMode.getTurn().getPlayer().getLeaderCards().get(1).setIsActive();
        marbles = multiPlayerMode.getMarketTray().peekMarbles(MarketChoice.ROW, 2);
        List<Integer> effects = new ArrayList<>();
        for(Marble m: marbles)
        {
            if(m.getColor()== ColorMarble.WHITE)
                effects.add(1);
        }
        takeResourcesFromMarketMessage.setWhiteEffects(effects);
        List<Pair<ResourceType, MarbleDestination>> expectedPairList = new ArrayList<>();
        for(Marble m: marbles)
        {
            if(m.getColor()== ColorMarble.BLUE) expectedPairList.add(new Pair<ResourceType, MarbleDestination>(ResourceType.shield,MarbleDestination.DISCARD));
            else if(m.getColor()== ColorMarble.GREY) expectedPairList.add(new Pair<ResourceType, MarbleDestination>(ResourceType.stone,MarbleDestination.DISCARD));
            else if(m.getColor()== ColorMarble.PURPLE) expectedPairList.add(new Pair<ResourceType, MarbleDestination>(ResourceType.servant,MarbleDestination.DISCARD));
            else if(m.getColor()== ColorMarble.YELLOW) expectedPairList.add(new Pair<ResourceType, MarbleDestination>(ResourceType.coin,MarbleDestination.DISCARD));
            else if(m.getColor()== ColorMarble.WHITE) expectedPairList.add(new Pair<ResourceType, MarbleDestination>(ResourceType.shield,MarbleDestination.DISCARD));
        }
        List<Pair<Marble, MarbleDestination>> pairList = new ArrayList<>();
        for(Marble m: marbles)
        {
            pairList.add(new Pair<Marble, MarbleDestination>(m,MarbleDestination.DISCARD));
        }
        takeResourcesFromMarketMessage.setWhereToPutMarbles(pairList);
        multiPlayerMode.getTurn().setTurnState(TurnState.ActionType.TAKERESOURCESFROMMARKET);
        multiPlayerMode.getTurn().getTurnPhase().takeResourcesFromMarket(multiPlayerMode.getTurn(),takeResourcesFromMarketMessage);
        TakeResourcesFromMarket takeResourcesFromMarket = (TakeResourcesFromMarket) multiPlayerMode.getTurn().getTurnPhase();
        assertTrue(expectedPairList.containsAll(takeResourcesFromMarket.getWhereToPutResources()) && takeResourcesFromMarket.getWhereToPutResources().containsAll(expectedPairList));
    }

    @Test
    void takeResourcesFromMarketException()
    {
        TakeResourcesFromMarketMessage takeResourcesFromMarketMessage = new TakeResourcesFromMarketMessage();
        takeResourcesFromMarketMessage.setPlayerChoice(MarketChoice.ROW, 2);
        multiPlayerMode.getTurn().normalActionDone();
        multiPlayerMode.getTurn().setTurnState(TurnState.ActionType.TAKERESOURCESFROMMARKET);
        assertThrows(InvalidActionException.class,()->multiPlayerMode.getTurn().getTurnPhase().takeResourcesFromMarket(multiPlayerMode.getTurn(),takeResourcesFromMarketMessage));
    }
    @Test
    void checkValidWhiteEffectsok() throws InvalidActionException, WhiteEffectMismatchException {
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
        multiPlayerMode.getTurn().setTurnState(TurnState.ActionType.TAKERESOURCESFROMMARKET);
        multiPlayerMode.getTurn().getTurnPhase().takeResourcesFromMarket(multiPlayerMode.getTurn(),message);
        TakeResourcesFromMarket takeResourcesFromMarket = (TakeResourcesFromMarket) multiPlayerMode.getTurn().getTurnPhase();
        assertTrue(takeResourcesFromMarket.checkValidWhiteEffects(multiPlayerMode.getTurn(), message.getWhiteEffects(),message.getRequestedMarbles()));
    }

    @Test
    void checkValidWhiteEffectsKo() throws InvalidActionException, WhiteEffectMismatchException {
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
        List<Integer> effects = new ArrayList<>();
        effects.add(1);
        effects.add(2);
        message.setWhiteEffects(effects);
        message.setWhereToPutMarbles(wheretoPutMarbles);
        multiPlayerMode.getTurn().setTurnState(TurnState.ActionType.TAKERESOURCESFROMMARKET);
        assertThrows(WhiteEffectMismatchException.class,()->multiPlayerMode.getTurn().getTurnPhase().takeResourcesFromMarket(multiPlayerMode.getTurn(),message));
        TakeResourcesFromMarket takeResourcesFromMarket = (TakeResourcesFromMarket) multiPlayerMode.getTurn().getTurnPhase();
        assertFalse(takeResourcesFromMarket.checkValidWhiteEffects(multiPlayerMode.getTurn(), message.getWhiteEffects(),message.getRequestedMarbles()));
    }

    @Test
    void handlePositioning() throws DepotOutOfBoundsException, IncompatibleResourceTypeException, InvalidActionException, WhiteEffectMismatchException {
        List<Marble> marbles;
        multiPlayerMode.getTurn().getPlayer().getPersonalBoard().getWarehouse().addResourcesToDepot(1,ResourceType.coin,1);
        multiPlayerMode.getTurn().getPlayer().getPersonalBoard().getWarehouse().addResourcesToDepot(2,ResourceType.shield,2);
        multiPlayerMode.getTurn().getPlayer().getPersonalBoard().getWarehouse().addResourcesToDepot(3,ResourceType.servant,2);
        TakeResourcesFromMarketMessage takeResourcesFromMarketMessage = new TakeResourcesFromMarketMessage();
        takeResourcesFromMarketMessage.setPlayerChoice(MarketChoice.ROW, 2);
        multiPlayerMode.getTurn().getPlayer().getLeaderCards().clear();
        multiPlayerMode.getTurn().getPlayer().getLeaderCards().add(new LeaderCard(new LeaderEffect(Effect.CMARBLE,ResourceType.shield),null,null,0));
        multiPlayerMode.getTurn().getPlayer().getLeaderCards().add(new LeaderCard(new LeaderEffect(Effect.CMARBLE,ResourceType.coin),null,null,0));
        multiPlayerMode.getTurn().getPlayer().getLeaderCards().get(0).setIsActive();
        multiPlayerMode.getTurn().getPlayer().getLeaderCards().get(1).setIsActive();
        marbles = multiPlayerMode.getMarketTray().peekMarbles(MarketChoice.ROW, 2);
        List<Integer> effects = new ArrayList<>();
        for(Marble m: marbles)
        {
            if(m.getColor()== ColorMarble.WHITE)
                effects.add(1);
        }
        takeResourcesFromMarketMessage.setWhiteEffects(effects);
        int pendingResources = 0;
        boolean servant = false;
        List<Pair<Marble, MarbleDestination>> pairList = new ArrayList<>();
        for(Marble m: marbles)
        {
            if(m.getColor()== ColorMarble.YELLOW) {
                pendingResources++;
                pairList.add(new Pair<Marble, MarbleDestination>(m, MarbleDestination.DEPOT1));
            }
            else
                if(m.getColor()== ColorMarble.GREY){
                    pendingResources++;
                    pairList.add(new Pair<Marble, MarbleDestination>(m,MarbleDestination.DEPOT2));
                }
                else
                if(m.getColor()== ColorMarble.PURPLE)
                {
                    if (servant) pendingResources++;
                    servant = true;
                    pairList.add(new Pair<Marble, MarbleDestination>(m, MarbleDestination.DEPOT3));
                }
                else
                if(m.getColor()== ColorMarble.WHITE){
                    pendingResources++;
                    pairList.add(new Pair<Marble, MarbleDestination>(m,MarbleDestination.DEPOT3));}
                else pairList.add(new Pair<Marble, MarbleDestination>(m,MarbleDestination.DISCARD));
        }
        takeResourcesFromMarketMessage.setWhereToPutMarbles(pairList);
        multiPlayerMode.getTurn().setTurnState(TurnState.ActionType.TAKERESOURCESFROMMARKET);
        multiPlayerMode.getTurn().getTurnPhase().takeResourcesFromMarket(multiPlayerMode.getTurn(),takeResourcesFromMarketMessage);
        TakeResourcesFromMarket takeResourcesFromMarket = (TakeResourcesFromMarket) multiPlayerMode.getTurn().getTurnPhase();
        assertEquals(multiPlayerMode.getTurn().getPlayer().getPersonalBoard().getWarehouse().getNormalDepots().get(0).getOcc(),1);
        assertEquals(multiPlayerMode.getTurn().getPlayer().getPersonalBoard().getWarehouse().getNormalDepots().get(1).getOcc(),2);
        if(servant)
            assertEquals(multiPlayerMode.getTurn().getPlayer().getPersonalBoard().getWarehouse().getNormalDepots().get(2).getOcc(),3);
        else
            assertEquals(multiPlayerMode.getTurn().getPlayer().getPersonalBoard().getWarehouse().getNormalDepots().get(2).getOcc(),2);
        assertEquals(pendingResources,takeResourcesFromMarket.getPendingResources().size());
    }

}