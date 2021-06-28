package it.polimi.ingsw.model;

import it.polimi.ingsw.commons.*;
import it.polimi.ingsw.exceptions.DepotOutOfBoundsException;
import it.polimi.ingsw.exceptions.IncompatibleResourceTypeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player player = new Player("pippo");

    @BeforeEach
    void initialization(){

        Map<ResourceType, Integer> requirementsResources1 = new EnumMap<>(ResourceType.class);
        Map<ResourceType, Integer> requirementsResources2 = new EnumMap<>(ResourceType.class);
        List<CardType> requirementsCards1 = new ArrayList<>();
        List<CardType> requirementsCards2 = new ArrayList<>();
        requirementsCards2.add(new CardType(2, ColorCard.yellow));
        requirementsResources1.put(ResourceType.coin, 2);
        LeaderCard leaderCard1 = new LeaderCard(new LeaderEffect(Effect.EXTRADEPOT, ResourceType.coin), requirementsResources1, requirementsCards1, 5);
        LeaderCard leaderCard2 = new LeaderCard(new LeaderEffect(Effect.EXTRADEPOT, ResourceType.shield), requirementsResources2, requirementsCards2, 3);

        player.getLeaderCards().add(leaderCard1);
        player.getLeaderCards().add(leaderCard2);

    }

    @Test
    void initialize() {
        assertEquals("pippo", player.getUser().getNickname());
    }

    @Test
    void activateLeaderCard() {

        player.activateLeaderCard(0);

        assertTrue(player.getLeaderCards().get(0).isActive());
        assertFalse(player.getLeaderCards().get(1).isActive());

        player.activateLeaderCard(1);

        assertTrue(player.getLeaderCards().get(0).isActive());
        assertTrue(player.getLeaderCards().get(1).isActive());

        assertNotNull(player.getPersonalBoard().getWarehouse().getExtraDepots().get(0));
        assertNotNull(player.getPersonalBoard().getWarehouse().getExtraDepots().get(0));
    }

    @Test
    void discardLeaderCard() {
        int initialPosition = player.getPersonalBoard().getFaithTrack().getFaithMarker();
        LeaderCard toDiscard1 = player.getLeaderCards().get(1);

        player.discardLeaderCard(1);

        assertFalse(player.getLeaderCards().contains(toDiscard1));
        assertEquals(initialPosition+1, player.getPersonalBoard().getFaithTrack().getFaithMarker());

        LeaderCard toDiscard2 = player.getLeaderCards().get(0);

        player.discardLeaderCard(0);

        assertFalse(player.getLeaderCards().contains(toDiscard2));
        assertEquals(initialPosition+2, player.getPersonalBoard().getFaithTrack().getFaithMarker());
    }


    @Test
    void getUsername() {
        Player playerFoo = new Player("foo");
        assertEquals("foo", playerFoo.getUser().getNickname());
    }

    @Test
    void getLeaderCards() {
    }

    @Test
    void getPosition() {
    }

    @Test
    void getPersonalBoard() {
    }

    @Test
    void initializePersonalBoard() {
        Player player3 = new Player("player3");
        assertNotNull(player3.getPersonalBoard());
    }

    @Test
    void setPosition() {
        player.setPosition(3);
        assertEquals(3, player.getPosition());
    }

    @Test
    void getActiveEffects() {
        LeaderCard toActive1 = player.getLeaderCards().get(0);
        player.activateLeaderCard(0);
        List<LeaderEffect> expectedActiveEffects = new ArrayList<>();
        expectedActiveEffects.add(toActive1.getLeaderEffect());

        assertEquals(expectedActiveEffects, player.getActiveEffects());

        LeaderCard toActive2 = player.getLeaderCards().get(1);
        player.activateLeaderCard(1);
        expectedActiveEffects.add(toActive2.getLeaderEffect());

        assertEquals(expectedActiveEffects, player.getActiveEffects());
    }

    @Test
    void calcVictoryPointsPlayer() throws DepotOutOfBoundsException, IncompatibleResourceTypeException {
        Warehouse warehouse = player.getPersonalBoard().getWarehouse();
        Map<ResourceType,Integer> startingresource = new EnumMap<ResourceType, Integer>(ResourceType.class);

        warehouse.addResourcesToDepot(3, ResourceType.shield, 3);
        warehouse.addResourcesToDepot(2, ResourceType.coin, 2);
        warehouse.addResourcesToDepot(1, ResourceType.stone, 1);
        startingresource.put(ResourceType.servant,2);
        startingresource.put(ResourceType.coin,3);
        startingresource.put(ResourceType.stone,4);
        startingresource.put(ResourceType.shield,1);
        warehouse.addResourcesToStrongbox(startingresource);//3 VP

        player.getLeaderCards().get(0).setIsActive();//5 VP

        player.getPersonalBoard().moveMarker(player, 19);//12 VP

        Map<ResourceType, Integer> cost = new EnumMap<ResourceType, Integer>(ResourceType.class);
        cost.put(ResourceType.servant,0);
        cost.put(ResourceType.coin,0);
        cost.put(ResourceType.stone,0);
        cost.put(ResourceType.shield,0);
        player.getPersonalBoard().putCardOnTop(new DevelopmentCard(cost,1,ColorCard.blue,3,null),1);
        player.getPersonalBoard().putCardOnTop(new DevelopmentCard(cost,2,ColorCard.yellow,5,null),1);
        player.getPersonalBoard().putCardOnTop(new DevelopmentCard(cost,1,ColorCard.purple,2,null),2);
        player.getPersonalBoard().putCardOnTop(new DevelopmentCard(cost,1,ColorCard.green,3,null),3);

        player.getPersonalBoard().getFaithTrack().setFavorTile(0,StateFavorTiles.FACEUP);
        player.getPersonalBoard().getFaithTrack().setFavorTile(1,StateFavorTiles.FACEUP);
        //5 VP FAVOR TILES

        assertEquals(38,player.calcVictoryPointsPlayer());

    }
}