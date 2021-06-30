package it.polimi.ingsw.model;

import it.polimi.ingsw.commons.*;
import it.polimi.ingsw.exceptions.DepotOutOfBoundsException;
import it.polimi.ingsw.exceptions.IncompatibleResourceTypeException;
import it.polimi.ingsw.model.gameEvents.BlackCrossHitLastSpace;
import it.polimi.ingsw.model.gameEvents.DevCardColorEnded;
import it.polimi.ingsw.model.gameEvents.HitLastSpace;
import it.polimi.ingsw.model.gameEvents.SeventhDevCardBought;
import it.polimi.ingsw.model.soloMode.SoloMode;
import it.polimi.ingsw.utils.Pair;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MultiPlayerModeTest {


    List<Player> playerList = new ArrayList<>();


    public List<Player> getPlayerList() {
        return playerList;
    }


    @Test
    void setup() {
        playerList.add(new Player("Piero"));
        playerList.add(new Player("Domenico"));
        playerList.add(new Player("Andrea"));
        MultiPlayerMode multiPlayerMode = new MultiPlayerMode(playerList.get(0),playerList,playerList.get(0),playerList.size());
        multiPlayerMode.setup();
        assertNotNull(multiPlayerMode.getDecks());
        assertNotNull(multiPlayerMode.getCurrPlayer());
        assertNotNull(multiPlayerMode.getMarketTray());
        for(Player p:playerList) {
            assertNotNull(multiPlayerMode.getPlayerList().get(getPlayerList().indexOf(p)).getPersonalBoard());
            assertNotNull(multiPlayerMode.getPlayerList().get(getPlayerList().indexOf(p)).getLeaderCards());
            assertEquals(getPlayerList().indexOf(p)+1, multiPlayerMode.getPlayerList().get(getPlayerList().indexOf(p)).getPosition());
        }
    }

    @Test
    void activateVaticanReport() {
        playerList.add(new Player("Piero"));
        playerList.add(new Player("Domenico"));
        playerList.add(new Player("Andrea"));
        MultiPlayerMode multiPlayerMode = new MultiPlayerMode(playerList.get(0),playerList,playerList.get(0),playerList.size());
        multiPlayerMode.setup();
        assertEquals(StateFavorTiles.FACEDOWN, multiPlayerMode.getCurrPlayer().getPersonalBoard().getFaithTrack().getSections()[0].getState());
        assertEquals(StateFavorTiles.FACEDOWN, multiPlayerMode.getPlayerList().get(1).getPersonalBoard().getFaithTrack().getSections()[0].getState());
        assertEquals(StateFavorTiles.FACEDOWN, multiPlayerMode.getPlayerList().get(2).getPersonalBoard().getFaithTrack().getSections()[0].getState());
        multiPlayerMode.getCurrPlayer().getPersonalBoard().moveMarker(multiPlayerMode.currPlayer, 8);
        assertEquals(StateFavorTiles.FACEUP, multiPlayerMode.getCurrPlayer().getPersonalBoard().getFaithTrack().getSections()[0].getState());
        assertEquals(StateFavorTiles.DISCARDED, multiPlayerMode.getPlayerList().get(1).getPersonalBoard().getFaithTrack().getSections()[0].getState());
        assertEquals(StateFavorTiles.DISCARDED, multiPlayerMode.getPlayerList().get(2).getPersonalBoard().getFaithTrack().getSections()[0].getState());
    }

    @Test
    void nextPlayer()
    {
        playerList.add(new Player("Piero"));
        playerList.add(new Player("Domenico"));
        playerList.add(new Player("Andrea"));
        playerList.add(new Player("Pippo"));
        MultiPlayerMode multiPlayerMode = new MultiPlayerMode(playerList.get(0),playerList,playerList.get(0),playerList.size());
        multiPlayerMode.setup();
        playerList.get(1).getUser().setActive(false);
        playerList.get(2).getUser().setActive(false);
        System.out.println(multiPlayerMode.nextPlayer(multiPlayerMode.getCurrPlayer()));
    }

    @Test
    void conclusionEvents(){
        Player first = spy(new Player("Andrea"));
        Player second = spy(new Player("Piero"));
        Player third = spy(new Player("Domenico"));
        List<Player> players = new ArrayList<>();
        players.add(first);
        players.add(second);
        players.add(third);

        MultiPlayerMode game = new MultiPlayerMode(players);
        MultiPlayerMode gameSpy = spy(game);

        HitLastSpace hitLastSpaceEvent = new HitLastSpace();
        gameSpy.endGame(hitLastSpaceEvent);
        verify(gameSpy, times(1)).endGame(hitLastSpaceEvent);

        SeventhDevCardBought seventhDevCardBoughtEvent = new SeventhDevCardBought();
        gameSpy.endGame(seventhDevCardBoughtEvent);
        verify(gameSpy, times(1)).endGame(seventhDevCardBoughtEvent);

        BlackCrossHitLastSpace blackCrossHitLastSpaceEvent = new BlackCrossHitLastSpace();
        gameSpy.endGame(blackCrossHitLastSpaceEvent);
        verify(gameSpy, times(1)).endGame(blackCrossHitLastSpaceEvent);

        DevCardColorEnded devCardColorEndedEvent = new DevCardColorEnded();
        gameSpy.endGame(devCardColorEndedEvent);
        verify(gameSpy, times(1)).endGame(devCardColorEndedEvent);
    }

    @Test
    void concludeGame() throws DepotOutOfBoundsException, IncompatibleResourceTypeException {
        playerList.add(new Player("Piero"));
        playerList.add(new Player("Domenico"));
        playerList.add(new Player("Andrea"));
        playerList.add(new Player("Mario"));
        MultiPlayerMode multiPlayerMode = new MultiPlayerMode(playerList.get(0),playerList,playerList.get(0),playerList.size());
        multiPlayerMode.setup();
        Map<ResourceType,Integer> startingresource = new EnumMap<ResourceType, Integer>(ResourceType.class);

        for(int i=0;i<4;i++) {
            playerList.get(i).getPersonalBoard().getWarehouse().addResourcesToDepot(3, ResourceType.shield, 3);
            playerList.get(i).getPersonalBoard().getWarehouse().addResourcesToDepot(2, ResourceType.coin, 2);
            playerList.get(i).getPersonalBoard().getWarehouse().addResourcesToDepot(1, ResourceType.stone, 1);
            startingresource.put(ResourceType.servant, 2);
            startingresource.put(ResourceType.coin, 3);
            startingresource.put(ResourceType.stone, 4);
            startingresource.put(ResourceType.shield, 1);
            playerList.get(i).getPersonalBoard().getWarehouse().addResourcesToStrongbox(startingresource);
            playerList.get(i).getLeaderCards().clear();
        }
        playerList.get(0).getLeaderCards().add(new LeaderCard(new LeaderEffect(Effect.EXTRAPRODUCTION, ResourceType.coin), null, null, 5));
        playerList.get(0).getLeaderCards().get(0).setIsActive();
        playerList.get(1).getLeaderCards().add(new LeaderCard(new LeaderEffect(Effect.EXTRAPRODUCTION, ResourceType.coin), null, null, 3));
        playerList.get(1).getLeaderCards().add(new LeaderCard(new LeaderEffect(Effect.EXTRAPRODUCTION, ResourceType.coin), null, null, 5));
        playerList.get(1).getLeaderCards().get(0).setIsActive();
        playerList.get(1).getLeaderCards().get(1).setIsActive();
        playerList.get(2).getLeaderCards().add(new LeaderCard(new LeaderEffect(Effect.EXTRAPRODUCTION, ResourceType.coin), null, null, 5));
        playerList.get(2).getLeaderCards().add(new LeaderCard(new LeaderEffect(Effect.EXTRAPRODUCTION, ResourceType.coin), null, null, 5));
        playerList.get(2).getLeaderCards().get(0).setIsActive();
        playerList.get(2).getLeaderCards().get(1).setIsActive();
        playerList.get(3).getLeaderCards().add(new LeaderCard(new LeaderEffect(Effect.EXTRAPRODUCTION, ResourceType.coin), null, null, 2));
        playerList.get(3).getLeaderCards().get(0).setIsActive();

        playerList.get(0).getPersonalBoard().moveMarker(playerList.get(0), 8);
        playerList.get(3).getPersonalBoard().moveMarker(playerList.get(3), 11);
        playerList.get(1).getPersonalBoard().moveMarker(playerList.get(1), 5);
        playerList.get(2).getPersonalBoard().moveMarker(playerList.get(2), 15);

        Map<ResourceType, Integer> cost = new EnumMap<ResourceType, Integer>(ResourceType.class);
        cost.put(ResourceType.servant,0);
        cost.put(ResourceType.coin,0);
        cost.put(ResourceType.stone,0);
        cost.put(ResourceType.shield,0);
        playerList.get(0).getPersonalBoard().putCardOnTop(new DevelopmentCard(cost,1, ColorCard.blue,3,null),1);
        playerList.get(0).getPersonalBoard().putCardOnTop(new DevelopmentCard(cost,2,ColorCard.yellow,5,null),1);
        playerList.get(0).getPersonalBoard().putCardOnTop(new DevelopmentCard(cost,1,ColorCard.purple,2,null),2);
        playerList.get(0).getPersonalBoard().putCardOnTop(new DevelopmentCard(cost,1,ColorCard.green,3,null),3);

        playerList.get(1).getPersonalBoard().putCardOnTop(new DevelopmentCard(cost,1, ColorCard.blue,2,null),1);
        playerList.get(1).getPersonalBoard().putCardOnTop(new DevelopmentCard(cost,2,ColorCard.yellow,4,null),1);

        playerList.get(2).getPersonalBoard().putCardOnTop(new DevelopmentCard(cost,1, ColorCard.blue,1,null),1);
        playerList.get(2).getPersonalBoard().putCardOnTop(new DevelopmentCard(cost,2,ColorCard.yellow,2,null),1);
        playerList.get(2).getPersonalBoard().putCardOnTop(new DevelopmentCard(cost,1, ColorCard.blue,4,null),2);

        playerList.get(3).getPersonalBoard().putCardOnTop(new DevelopmentCard(cost,1, ColorCard.blue,3,null),1);

        int victoryPoints1 = playerList.get(0).calcVictoryPointsPlayer();
        int victoryPoints2 = playerList.get(1).calcVictoryPointsPlayer();
        int victoryPoints3 = playerList.get(2).calcVictoryPointsPlayer();
        int victoryPoints4 = playerList.get(3).calcVictoryPointsPlayer();

        List<Pair<User, Integer>> expectedRank = new ArrayList<>();

        expectedRank.add(new Pair<>(playerList.get(2).getUser(), victoryPoints3));
        expectedRank.add(new Pair<>(playerList.get(0).getUser(), victoryPoints1));
        expectedRank.add(new Pair<>(playerList.get(1).getUser(), victoryPoints2));
        expectedRank.add(new Pair<>(playerList.get(3).getUser(), victoryPoints4));

        assertEquals(expectedRank, multiPlayerMode.concludeGame());
    }
}