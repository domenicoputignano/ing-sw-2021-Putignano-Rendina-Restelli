package it.polimi.ingsw.Model;

import it.polimi.ingsw.Client.ReducedMarble;
import it.polimi.ingsw.Commons.*;
import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Model.MarketTray.Marble;
import it.polimi.ingsw.Model.MarketTray.WhiteMarble;
import it.polimi.ingsw.Utils.MarbleDestination;
import it.polimi.ingsw.Utils.MarketChoice;
import it.polimi.ingsw.Utils.Pair;
import it.polimi.ingsw.Utils.Messages.ClientMessages.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    void takeResourcesFromMarket() throws InvalidActionException, WhiteEffectMismatchException, NeedPositioningException {
        List<Marble> marbles;
        TakeResourcesFromMarketMessage takeResourcesFromMarketMessage = new TakeResourcesFromMarketMessage();
        takeResourcesFromMarketMessage.setPlayerChoice(MarketChoice.ROW, 2);
        multiPlayerMode.getTurn().getPlayer().getLeaderCards().clear();
        multiPlayerMode.getTurn().getPlayer().getLeaderCards().add(new LeaderCard(new LeaderEffect(Effect.CMARBLE, ResourceType.shield),null,null,0));
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
            takeResourcesFromMarketMessage.addWhereToPutMarbles(new Pair<ReducedMarble, MarbleDestination>(m.getReducedVersion(),MarbleDestination.DISCARD));
        }
        multiPlayerMode.getTurn().setTurnState(TurnState.ActionType.TAKERESOURCESFROMMARKET);
        multiPlayerMode.getTurn().getTurnPhase().takeResourcesFromMarket(multiPlayerMode.getTurn(),takeResourcesFromMarketMessage);
        TakeResourcesFromMarket takeResourcesFromMarket = (TakeResourcesFromMarket) multiPlayerMode.getTurn().getTurnPhase();
        assertTrue(expectedPairList.containsAll(takeResourcesFromMarket.getWhereToPutResources()) && takeResourcesFromMarket.getWhereToPutResources().containsAll(expectedPairList));
    }

    @Test
    void takeResourcesFromMarketWhiteEffectMismatchException() throws InvalidActionException, WhiteEffectMismatchException {
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
        effects.add(2);
        for(Marble m: marbles)
        {
            if(m.getColor()== ColorMarble.WHITE)
                effects.add(1);
        }
        takeResourcesFromMarketMessage.setWhiteEffects(effects);
        List<Pair<Marble, MarbleDestination>> pairList = new ArrayList<>();
        for(Marble m: marbles)
        {
            takeResourcesFromMarketMessage.addWhereToPutMarbles(new Pair<ReducedMarble, MarbleDestination>(m.getReducedVersion(),MarbleDestination.DISCARD));
        }
        multiPlayerMode.getTurn().setTurnState(TurnState.ActionType.TAKERESOURCESFROMMARKET);
        assertThrows(WhiteEffectMismatchException.class,()->multiPlayerMode.getTurn().getTurnPhase().takeResourcesFromMarket(multiPlayerMode.getTurn(),takeResourcesFromMarketMessage));
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

    @RepeatedTest(5)
    void checkValidWhiteEffectsOk() {
        TakeResourcesFromMarketMessage message = new TakeResourcesFromMarketMessage();
        message.setPlayerChoice(MarketChoice.ROW, 2);
        multiPlayerMode.getTurn().getPlayer().getLeaderCards().clear();
        multiPlayerMode.getTurn().getPlayer().getLeaderCards().add(new LeaderCard(new LeaderEffect(Effect.CMARBLE,ResourceType.shield),null,null,0));
        multiPlayerMode.getTurn().getPlayer().getLeaderCards().add(new LeaderCard(new LeaderEffect(Effect.CMARBLE,ResourceType.coin),null,null,0));
        multiPlayerMode.getTurn().getPlayer().getLeaderCards().get(0).setIsActive();
        multiPlayerMode.getTurn().getPlayer().getLeaderCards().get(1).setIsActive();
        List<Marble> marbles = multiPlayerMode.getMarketTray().peekMarbles(MarketChoice.ROW, 2);
        List<ReducedMarble> requestedMarbles = marbles.stream().map(Marble::getReducedVersion).collect(Collectors.toList());
        int i = 0;
        for(Marble marble : marbles) {
            if((marble.getColor() == ColorMarble.WHITE) && (i%2 == 0)) {
                message.addWhiteEffect(1);
                i++;
            } else if((marble.getColor() == ColorMarble.WHITE) && (i%2 != 0)) {
                message.addWhiteEffect(2);
                i++;
            }
        }
        multiPlayerMode.getTurn().setTurnState(TurnState.ActionType.TAKERESOURCESFROMMARKET);
        TakeResourcesFromMarket takeResourcesFromMarket = (TakeResourcesFromMarket) multiPlayerMode.getTurn().getTurnPhase();
        assertTrue(takeResourcesFromMarket.checkValidWhiteEffects(multiPlayerMode.getTurn(), message.getWhiteEffects(), requestedMarbles));
    }

    //TODO cambiare il test
    @RepeatedTest(5)
    void checkValidWhiteEffectsKo() throws InvalidActionException, WhiteEffectMismatchException {
        TakeResourcesFromMarketMessage message = new TakeResourcesFromMarketMessage();
        message.setPlayerChoice(MarketChoice.ROW, 2);
        multiPlayerMode.getTurn().getPlayer().getLeaderCards().clear();
        multiPlayerMode.getTurn().getPlayer().getLeaderCards().add(new LeaderCard(new LeaderEffect(Effect.CMARBLE,ResourceType.shield),null,null,0));
        multiPlayerMode.getTurn().getPlayer().getLeaderCards().add(new LeaderCard(new LeaderEffect(Effect.CMARBLE,ResourceType.coin),null,null,0));
        multiPlayerMode.getTurn().getPlayer().getLeaderCards().get(0).setIsActive();
        multiPlayerMode.getTurn().getPlayer().getLeaderCards().get(1).setIsActive();
        List<Marble> marbles = multiPlayerMode.getMarketTray().peekMarbles(MarketChoice.ROW, 2);
        List<ReducedMarble> requestedMarbles = marbles.stream().map(Marble::getReducedVersion).collect(Collectors.toList());
        int i = 0;
        for(Marble marble : marbles) {
            if((marble.getColor() == ColorMarble.WHITE) && (i != 0)) {
                message.addWhiteEffect(1);
                i++;
            }
        }
        multiPlayerMode.getTurn().setTurnState(TurnState.ActionType.TAKERESOURCESFROMMARKET);
        TakeResourcesFromMarket takeResourcesFromMarket = (TakeResourcesFromMarket) multiPlayerMode.getTurn().getTurnPhase();
        if(requestedMarbles.stream().anyMatch(x -> x.getColorMarble() == ColorMarble.WHITE)){
            assertFalse(takeResourcesFromMarket.checkValidWhiteEffects(multiPlayerMode.getTurn(), message.getWhiteEffects(),requestedMarbles));
        }
    }

    @RepeatedTest(5)
    void handlePositioning() throws DepotOutOfBoundsException, IncompatibleResourceTypeException, InvalidActionException, WhiteEffectMismatchException, NeedPositioningException {
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
        for(Marble m: marbles)
        {
            if(m.getColor()== ColorMarble.YELLOW) {
                pendingResources++;
                takeResourcesFromMarketMessage.addWhereToPutMarbles(new Pair<ReducedMarble, MarbleDestination>(m.getReducedVersion(), MarbleDestination.DEPOT1));
            }
            else
                if(m.getColor()== ColorMarble.GREY){
                    pendingResources++;
                    takeResourcesFromMarketMessage.addWhereToPutMarbles(new Pair<ReducedMarble, MarbleDestination>(m.getReducedVersion(),MarbleDestination.DEPOT2));
                }
                else
                if(m.getColor()== ColorMarble.PURPLE)
                {
                    if (servant) pendingResources++;
                    servant = true;
                    takeResourcesFromMarketMessage.addWhereToPutMarbles(new Pair<ReducedMarble, MarbleDestination>(m.getReducedVersion(), MarbleDestination.DEPOT3));
                }
                else
                if(m.getColor()== ColorMarble.WHITE){
                    pendingResources++;
                    takeResourcesFromMarketMessage.addWhereToPutMarbles(new Pair<ReducedMarble, MarbleDestination>(m.getReducedVersion(),MarbleDestination.DEPOT3));}
                else takeResourcesFromMarketMessage.addWhereToPutMarbles(new Pair<ReducedMarble, MarbleDestination>(m.getReducedVersion(),MarbleDestination.DISCARD));
        }
        multiPlayerMode.getTurn().setTurnState(TurnState.ActionType.TAKERESOURCESFROMMARKET);
        assertThrows(NeedPositioningException.class, ()->multiPlayerMode.getTurn().getTurnPhase().takeResourcesFromMarket(multiPlayerMode.getTurn(),takeResourcesFromMarketMessage));
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